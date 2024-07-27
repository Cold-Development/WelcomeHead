package org.padrewin.welcomehead.listener;

import org.bukkit.entity.Player;
import org.padrewin.welcomehead.Utils.ImageChar;
import org.padrewin.welcomehead.Utils.ImageMessage;
import org.padrewin.welcomehead.Utils.Utils;
import org.padrewin.welcomehead.WelcomeHead;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class JoinEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PlayerJoinEvent e) throws IOException {
        final Player player = e.getPlayer(); // Capture 'e' in a final variable
        if (!player.hasPlayedBefore()) {
            final int spaceTop = WelcomeHead.getInstance().getConfig().getInt("Spaces-Top");
            final int spacesBot = WelcomeHead.getInstance().getConfig().getInt("Spaces-Bot");
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)WelcomeHead.getInstance(), new Runnable() {
                public void run() {
                    if (WelcomeHead.getInstance().getConfig().getBoolean("Players-FirstJoin.enable")) {
                        for (int i = 0; i <= spaceTop; i++)
                            player.sendMessage(" ");
                        BufferedImage imageToSend = null;
                        try {
                            imageToSend = ImageIO.read(new URL("https://minotar.net/avatar/" + player.getName() + "/8.png"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        (new ImageMessage(imageToSend, 8, ImageChar.BLOCK.getChar()))
                                .appendText(new String[] {
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.1"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.2"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.3"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.4"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.5"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.6"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.7"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-FirstJoin.head-text.8"))) })
                                .sendToPlayer(player);
                        for (int j = 0; j <= spacesBot; j++)
                            player.sendMessage(" ");
                    } else {
                        Utils.headNotActivated(player, WelcomeHead.getInstance().getConfig().getStringList("Players-FirstJoin.no-head-text"),
                                WelcomeHead.getInstance().getConfig().getBoolean("Players-FirstJoin.center"));
                    }
                    Utils.soundActivated(player, WelcomeHead.getInstance().getConfig().getBoolean("SoundA.enable"),
                            WelcomeHead.getInstance().getConfig().getString("SoundA.sound"),
                            WelcomeHead.getInstance().getConfig().getDouble("SoundA.volume"),
                            WelcomeHead.getInstance().getConfig().getDouble("SoundA.pitch"));
                    Utils.spawnFireworks(player.getLocation(), WelcomeHead.getInstance().getConfig().getInt("Firework.amount"));
                    Utils.commandsActivated(player, WelcomeHead.getInstance().getConfig().getStringList("Commands-First"), "Something is weird with your command -> First player");
                }
            }, WelcomeHead.getInstance().getConfig().getInt("Timer") * 20L);
        } else {
            final int spaceTop = WelcomeHead.getInstance().getConfig().getInt("Spaces-Top");
            final int spacesBot = WelcomeHead.getInstance().getConfig().getInt("Spaces-Bot");
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)WelcomeHead.getInstance(), new Runnable() {
                public void run() {
                    if (WelcomeHead.getInstance().getConfig().getBoolean("Players-Back.enable")) {
                        for (int i = 0; i <= spaceTop; i++)
                            player.sendMessage(" ");
                        BufferedImage imageToSend = null;
                        try {
                            imageToSend = ImageIO.read(new URL("https://minotar.net/avatar/" + player.getName() + "/8.png"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        (new ImageMessage(imageToSend, 8, ImageChar.BLOCK.getChar()))
                                .appendText(new String[] {
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.1"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.2"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.3"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.4"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.5"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.6"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.7"))),
                                        PlaceholderAPI.setPlaceholders(player, Utils.translateHexColorCodes("&#", "", WelcomeHead.getInstance().getConfig().getString("Players-Back.head-text.8"))) })
                                .sendToPlayer(player);
                        for (int j = 0; j <= spacesBot; j++)
                            player.sendMessage(" ");
                        Utils.soundActivated(player, WelcomeHead.getInstance().getConfig().getBoolean("SoundB.enable"),
                                WelcomeHead.getInstance().getConfig().getString("SoundB.sound"),
                                WelcomeHead.getInstance().getConfig().getDouble("SoundB.volume"),
                                WelcomeHead.getInstance().getConfig().getDouble("SoundB.pitch"));
                        Utils.commandsActivated(player, WelcomeHead.getInstance().getConfig().getStringList("Commands-Back"), "Something is weird with your command -> Back player");
                    } else {
                        for (int i = 0; i <= spacesBot; i++)
                            player.sendMessage(" ");
                        Utils.headNotActivated(player, WelcomeHead.getInstance().getConfig().getStringList("Players-Back.no-head-text"),
                                WelcomeHead.getInstance().getConfig().getBoolean("Players-Back.center"));
                        Utils.soundActivated(player, WelcomeHead.getInstance().getConfig().getBoolean("SoundB.enable"),
                                WelcomeHead.getInstance().getConfig().getString("SoundB.sound"),
                                WelcomeHead.getInstance().getConfig().getDouble("SoundB.volume"),
                                WelcomeHead.getInstance().getConfig().getDouble("SoundB.pitch"));
                        Utils.commandsActivated(player, WelcomeHead.getInstance().getConfig().getStringList("Commands-Back"), "Something is weird with your command -> Back player");
                        for (int i = 0; i <= spacesBot; i++)
                            player.sendMessage(" ");
                    }
                }
            }, WelcomeHead.getInstance().getConfig().getInt("Timer") * 20L);
        }
    }
}
