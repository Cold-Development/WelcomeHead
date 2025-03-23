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
        final int spaceTop = WelcomeHead.getInstance().getConfig().getInt("Spaces-Top");
        final int spacesBot = WelcomeHead.getInstance().getConfig().getInt("Spaces-Bot");

        if (!player.hasPlayedBefore()) {
            // Este primul login al jucătorului
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) WelcomeHead.getInstance(), new Runnable() {
                public void run() {
                    handleFirstJoin(player, spaceTop, spacesBot);
                }
            }, WelcomeHead.getInstance().getConfig().getInt("Timer") * 20L);
        } else {
            // Este un rejoin al jucătorului
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) WelcomeHead.getInstance(), new Runnable() {
                public void run() {
                    handleBackJoin(player, spaceTop, spacesBot);
                }
            }, WelcomeHead.getInstance().getConfig().getInt("Timer") * 20L);
        }
    }


    private void handleFirstJoin(Player player, int spaceTop, int spacesBot) {
        if (WelcomeHead.getInstance().getConfig().getBoolean("Players-FirstJoin.enable")) {
            sendEmptySpaces(player, spaceTop);
            sendImageMessage(player, "Players-FirstJoin.head-text");
            sendEmptySpaces(player, spacesBot);
        } else {
            Utils.headNotActivated(player,
                    WelcomeHead.getInstance().getConfig().getStringList("Players-FirstJoin.no-head-text"),
                    WelcomeHead.getInstance().getConfig().getBoolean("Players-FirstJoin.center"));
        }

        // Focuri de artificii
        int fireworkAmount = WelcomeHead.getInstance().getConfig().getInt("Firework.amount");
        Utils.spawnFireworks(player.getLocation(), fireworkAmount);

        // Sunete pentru primul login (apelează după artificii)
        Utils.soundActivated(player,
                WelcomeHead.getInstance().getConfig().getBoolean("SoundA.enable"),
                WelcomeHead.getInstance().getConfig().getString("SoundA.sound"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundA.volume"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundA.pitch"));

        // Comenzi pentru primul login
        Utils.commandsActivated(player,
                WelcomeHead.getInstance().getConfig().getStringList("Commands-First"),
                "Something is weird with your command -> First player");
    }



    private void handleBackJoin(Player player, int spaceTop, int spacesBot) {
        if (WelcomeHead.getInstance().getConfig().getBoolean("Players-Back.enable")) {
            sendEmptySpaces(player, spaceTop);
            sendImageMessage(player, "Players-Back.head-text");
            sendEmptySpaces(player, spacesBot);
        } else {
            Utils.headNotActivated(player,
                    WelcomeHead.getInstance().getConfig().getStringList("Players-Back.no-head-text"),
                    WelcomeHead.getInstance().getConfig().getBoolean("Players-Back.center"));
        }

        // Sunete pentru rejoin
        Utils.soundActivated(player,
                WelcomeHead.getInstance().getConfig().getBoolean("SoundB.enable"),
                WelcomeHead.getInstance().getConfig().getString("SoundB.sound"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundB.volume"),
                WelcomeHead.getInstance().getConfig().getDouble("SoundB.pitch"));

        Utils.commandsActivated(player,
                WelcomeHead.getInstance().getConfig().getStringList("Commands-Back"),
                "Something is weird with your command -> Back player");

    }


    private void sendEmptySpaces(Player player, int spaces) {
        for (int i = 0; i < spaces; i++) {
            player.sendMessage(" ");
        }
    }

    private void sendImageMessage(Player player, String configPath) {
        // Obține instanța DatabaseManager din plugin
        DatabaseManager dbManager = WelcomeHead.getInstance().getDatabaseManager();
        UUID playerUUID = player.getUniqueId();
        String playerName = player.getName();

        // Obține avatarul din cache (dacă există)
        CompletableFuture<BufferedImage> cachedFuture = dbManager.getCachedAvatar(playerUUID);

        // Descarcă avatarul actual de pe Minotar în mod asincron
        CompletableFuture<BufferedImage> downloadFuture = CompletableFuture.supplyAsync(() -> {
            BufferedImage downloadedImage = null;
            try {
                downloadedImage = ImageIO.read(new URL("https://minotar.net/avatar/" + playerName + "/8.png?ts=" + System.currentTimeMillis()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return downloadedImage;
        });

        // Combină rezultatele fără a bloca, folosind thenCompose
        CompletableFuture<BufferedImage> finalImageFuture = downloadFuture.thenCompose(downloaded -> {
            if (downloaded != null) {
                String newHash = DatabaseManager.computeImageHash(downloaded);
                return cachedFuture.thenCompose(cached -> {
                    if (cached != null) {
                        return dbManager.isAvatarUpdated(playerUUID, newHash).thenApply(isUpdated -> {
                            if (isUpdated) {
                                // Dacă skin-ul s-a schimbat, actualizează cache-ul și folosește imaginea descărcată
                                dbManager.storeAvatarInCache(playerUUID, playerName, downloaded);
                                return downloaded;
                            } else {
                                // Dacă nu s-a schimbat, folosește avatarul din cache
                                return cached;
                            }
                        });
                    } else {
                        // Nu există avatar în cache, deci stochează imaginea descărcată
                        dbManager.storeAvatarInCache(playerUUID, playerName, downloaded);
                        return CompletableFuture.completedFuture(downloaded);
                    }
                });
            } else {
                // Dacă descărcarea a eșuat, încearcă să folosești avatarul din cache
                return cachedFuture.thenApply(cached -> {
                    if (cached != null) {
                        return cached;
                    } else {
                        // Ca fallback, încarcă avatarul local default
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

        // Odată ce avem imaginea finală, revenim pe thread-ul principal pentru a interacționa cu API-ul Bukkit
        finalImageFuture.thenAccept(imageToSend -> {
            Bukkit.getScheduler().runTask(WelcomeHead.getInstance(), () -> {
                if (imageToSend != null) {
                    new ImageMessage(imageToSend, 8, ImageChar.BLOCK.getChar())
                            .appendText(getColoredText(player, configPath))
                            .sendToPlayer(player);
                } else {
                    player.sendMessage("Failed to load your avatar and the default avatar is also unavailable.");
                }
            });
        });
    }

    private String[] getColoredText(Player player, String configPath) {
        String[] textLines = new String[8];
        for (int i = 1; i <= 8; i++) {
            String line = WelcomeHead.getInstance().getConfig().getString(configPath + "." + i);
            if (line == null) {
                WelcomeHead.getInstance().getLogger().severe("Missing config for " + configPath + "." + i + ". Using an empty string.");
                line = "";
            }
            textLines[i - 1] = PlaceholderAPI.setPlaceholders(player, Utils.translateColors(line));
        }
        return textLines;
    }


    private void playEffectsAndCommands(Player player, String soundConfig, String commandsConfig, String errorMsg) {
        Utils.soundActivated(player,
                WelcomeHead.getInstance().getConfig().getBoolean(soundConfig + ".enable"),
                WelcomeHead.getInstance().getConfig().getString(soundConfig + ".sound"),
                WelcomeHead.getInstance().getConfig().getDouble(soundConfig + ".volume"),
                WelcomeHead.getInstance().getConfig().getDouble(soundConfig + ".pitch"));
        Utils.spawnFireworks(player.getLocation(), WelcomeHead.getInstance().getConfig().getInt("Firework.amount"));
        Utils.commandsActivated(player,
                WelcomeHead.getInstance().getConfig().getStringList(commandsConfig),
                "Something is weird with your command -> " + errorMsg);
    }
}
