package org.padrewin.welcomehead.database;

import org.padrewin.welcomehead.WelcomeHead;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class DatabaseManager {

    private final WelcomeHead plugin;
    private Connection connection;

    public DatabaseManager(WelcomeHead plugin, String dbName) {
        this.plugin = plugin;
        connect(dbName);
        createTables();
    }

    private void connect(String dbName) {
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            String dbPath = dataFolder.getAbsolutePath() + File.separator + dbName;
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            plugin.getLogger().info("Database connected using SQLite.");
        } catch (SQLException e) {
            plugin.getLogger().warning("Database failed to connect.");
            e.printStackTrace();
        }
    }

    private void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS cached_avatars (" +
                "player_uuid TEXT PRIMARY KEY, " +
                "player_name TEXT NOT NULL, " +
                "image BLOB NOT NULL, " +
                "hash TEXT NOT NULL, " +
                "last_updated INTEGER NOT NULL" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to create cached_avatars table!");
            e.printStackTrace();
        }
    }

    /**
     * Calculează hash-ul SHA-256 al unei imagini (format PNG).
     */
    public static String computeImageHash(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(imageBytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obține avatarul din cache pentru jucătorul specificat.
     * Returnează un BufferedImage sau null dacă nu există.
     */
    public CompletableFuture<BufferedImage> getCachedAvatar(UUID playerUUID) {
        return CompletableFuture.supplyAsync(() -> {
            String query = "SELECT image FROM cached_avatars WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, playerUUID.toString());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    byte[] imageBytes = rs.getBytes("image");
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                    return ImageIO.read(bais);
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to retrieve cached avatar for " + playerUUID);
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * Stochează sau actualizează avatarul jucătorului în cache-ul bazei de date.
     * Calculul hash-ului și salvarea se fac asincron.
     */
    public CompletableFuture<Void> storeAvatarInCache(UUID playerUUID, String playerName, BufferedImage image) {
        return CompletableFuture.runAsync(() -> {
            String hash = computeImageHash(image);
            long now = System.currentTimeMillis();
            String query = "INSERT INTO cached_avatars (player_uuid, player_name, image, hash, last_updated) VALUES (?, ?, ?, ?, ?)" +
                    " ON CONFLICT(player_uuid) DO UPDATE SET player_name = excluded.player_name, image = excluded.image, hash = excluded.hash, last_updated = excluded.last_updated";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, playerUUID.toString());
                stmt.setString(2, playerName);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                stmt.setBytes(3, imageBytes);
                stmt.setString(4, hash);
                stmt.setLong(5, now);
                stmt.executeUpdate();
            } catch (Exception e) {
                plugin.getLogger().severe("Failed to store avatar in cache for " + playerUUID);
                e.printStackTrace();
            }
        });
    }

    /**
     * Verifică dacă hash-ul noului avatar diferă de cel stocat.
     * Returnează true dacă se dorește actualizarea (sau dacă nu există intrare).
     */
    public CompletableFuture<Boolean> isAvatarUpdated(UUID playerUUID, String newHash) {
        return CompletableFuture.supplyAsync(() -> {
            String query = "SELECT hash FROM cached_avatars WHERE player_uuid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, playerUUID.toString());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String cachedHash = rs.getString("hash");
                    return !cachedHash.equals(newHash);
                }
            } catch (SQLException e) {
                plugin.getLogger().severe("Failed to check avatar hash for " + playerUUID);
                e.printStackTrace();
            }
            return true;
        });
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.getLogger().info("Database connection closed.");
            }
        } catch (SQLException e) {
            plugin.getLogger().severe("Failed to close the database connection!");
            e.printStackTrace();
        }
    }
}