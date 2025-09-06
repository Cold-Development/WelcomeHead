package org.padrewin.welcomehead.listener;

import org.bukkit.entity.Player;
import org.padrewin.welcomehead.Utils.ImageChar;
import org.padrewin.welcomehead.Utils.ImageMessage;
import org.padrewin.welcomehead.Utils.Utils;
import org.padrewin.welcomehead.WelcomeHead;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.padrewin.welcomehead.database.DatabaseManager;

public class JoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PlayerJoinEvent e) throws IOException {
        final Player player = e.getPlayer();
        // Read config values with names matching their actual behavior in Minecraft chat
        final int spacesBeforeText = WelcomeHead.getInstance().getConfig().getInt("Spaces-Top");
        final int spacesAfterText = WelcomeHead.getInstance().getConfig().getInt("Spaces-Bot");

        if (!player.hasPlayedBefore()) {
            // First login
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) WelcomeHead.getInstance(), new Runnable() {
                public void run() {
                    handleFirstJoin(player, spacesBeforeText, spacesAfterText);
                }
            }, WelcomeHead.getInstance().getConfig().getInt("Timer") * 20L);
        } else {
            // Returning player
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) WelcomeHead.getInstance(), new Runnable() {
                public void run() {
                    handleBackJoin(player, spacesBeforeText, spacesAfterText);
                }
            }, WelcomeHead.getInstance().getConfig().getInt("Timer") * 20L);
        }
    }

    private void handleFirstJoin(Player player, int spacesBeforeText, int spacesAfterText) {
        if (!WelcomeHead.getInstance().getConfig().getBoolean("Players-FirstJoin.enable")) {
            return; // do nothing
        }

        // read from config
        boolean showHead = WelcomeHead.getInstance().getConfig().getBoolean("Players-FirstJoin.showHead");
        boolean center   = WelcomeHead.getInstance().getConfig().getBoolean("Players-FirstJoin.center");
        String configPath = "Players-FirstJoin.head-text";

        // Send welcome message with appropriate spacing
        sendWelcomeMessage(player, configPath, showHead, center, spacesBeforeText, spacesAfterText);

        // Fireworks, sound, commands, etc.
        int fireworkAmount = WelcomeHead.getInstance().getConfig().getInt("Firework.amount");
        Utils.spawnFireworks(player.getLocation(), fireworkAmount);

        Utils.soundActivated(player,
                WelcomeHead.getInstance().getConfig().getBoolean("SoundA.enable"),
                WelcomeHead.getInstance().getConfig().getString("SoundA.sound"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundA.volume"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundA.pitch"));

        Utils.commandsActivated(player,
                WelcomeHead.getInstance().getConfig().getStringList("Commands-First"),
                "Something is weird with your command -> First player");
    }

    private void handleBackJoin(Player player, int spacesBeforeText, int spacesAfterText) {
        if (!WelcomeHead.getInstance().getConfig().getBoolean("Players-Back.enable")) {
            return; // do nothing
        }

        // read from config
        boolean showHead = WelcomeHead.getInstance().getConfig().getBoolean("Players-Back.showHead");
        boolean center   = WelcomeHead.getInstance().getConfig().getBoolean("Players-Back.center");
        String configPath = "Players-Back.head-text";

        // Send welcome message with appropriate spacing
        sendWelcomeMessage(player, configPath, showHead, center, spacesBeforeText, spacesAfterText);

        // Sounds, commands, etc.
        Utils.soundActivated(player,
                WelcomeHead.getInstance().getConfig().getBoolean("SoundB.enable"),
                WelcomeHead.getInstance().getConfig().getString("SoundB.sound"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundB.volume"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundB.pitch"));

        Utils.commandsActivated(player,
                WelcomeHead.getInstance().getConfig().getStringList("Commands-Back"),
                "Something is weird with your command -> Back player");
    }

    /**
     * Sends a welcome message to the player with appropriate spacing
     */
    private void sendWelcomeMessage(Player player, String configPath, boolean showHead, boolean center,
                                    int spacesBeforeText, int spacesAfterText) {
        // 1) Retrieve the 8 lines from the config
        String[] lines = new String[8];
        for (int i = 1; i <= 8; i++) {
            String line = WelcomeHead.getInstance().getConfig().getString(configPath + "." + i, "");
            line = PlaceholderAPI.setPlaceholders(player, Utils.translateColors(line));
            lines[i - 1] = line;
        }

        // 2) Center lines if needed
        if (center) {
            lines = Utils.centerLines(lines, 50);
        }

        // 3) If we're not showing head avatar, just send the text with spaces
        if (!showHead) {
            // Send spaces before (above) the message
            for (int i = 0; i < spacesBeforeText; i++) {
                player.sendMessage(" ");
            }

            // Send the actual message lines
            for (String line : lines) {
                player.sendMessage(line);
            }

            // Send spaces after (below) the message
            for (int i = 0; i < spacesAfterText; i++) {
                player.sendMessage(" ");
            }
            return;
        }

        // 4) If showing head avatar, need to handle async image loading
        DatabaseManager dbManager = WelcomeHead.getInstance().getDatabaseManager();
        UUID playerUUID = player.getUniqueId();
        String playerName = player.getName();

        CompletableFuture<BufferedImage> cachedFuture = dbManager.getCachedAvatar(playerUUID);
        CompletableFuture<BufferedImage> downloadFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return ImageIO.read(new URL("https://minotar.net/avatar/" + playerName + "/128.png?ts=" + System.currentTimeMillis()));
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        });

        CompletableFuture<BufferedImage> finalImageFuture = downloadFuture.thenCompose(downloaded -> {
            if (downloaded != null) {
                String newHash = DatabaseManager.computeImageHash(downloaded);
                return cachedFuture.thenCompose(cached -> {
                    if (cached != null) {
                        return dbManager.isAvatarUpdated(playerUUID, newHash).thenApply(isUpdated -> {
                            if (isUpdated) {
                                dbManager.storeAvatarInCache(playerUUID, playerName, downloaded);
                                return downloaded;
                            } else {
                                return cached;
                            }
                        });
                    } else {
                        dbManager.storeAvatarInCache(playerUUID, playerName, downloaded);
                        return CompletableFuture.completedFuture(downloaded);
                    }
                });
            } else {
                return cachedFuture.thenApply(cached -> {
                    if (cached != null) {
                        return cached;
                    } else {
                        try {
                            return ImageIO.read(Objects.requireNonNull(WelcomeHead.getInstance().getResource("default_avatar.png")));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            return null;
                        }
                    }
                });
            }
        });

        // 5) Once we have the final image, display everything in correct order
        final int finalSpacesBeforeText = spacesBeforeText;
        final int finalSpacesAfterText = spacesAfterText;
        final String[] finalLines = lines;

        finalImageFuture.thenAccept(image -> {
            Bukkit.getScheduler().runTask(WelcomeHead.getInstance(), () -> {
                if (image == null) {
                    // If everything fails, just send lines without image
                    // Send spaces before text
                    for (int i = 0; i < finalSpacesBeforeText; i++) {
                        player.sendMessage(" ");
                    }

                    // Send message
                    for (String line : finalLines) {
                        player.sendMessage(line);
                    }

                    // Send spaces after text
                    for (int i = 0; i < finalSpacesAfterText; i++) {
                        player.sendMessage(" ");
                    }
                    return;
                }

                // Send spaces before text
                for (int i = 0; i < finalSpacesBeforeText; i++) {
                    player.sendMessage(" ");
                }

                // Build and send the ImageMessage with text
                new ImageMessage(image, 8, ImageChar.BLOCK.getChar())
                        .appendText(finalLines)
                        .sendToPlayer(player);

                // Send spaces after text
                for (int i = 0; i < finalSpacesAfterText; i++) {
                    player.sendMessage(" ");
                }
            });
        });
    }
}