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
        final Player player = e.getPlayer();
        final int spaceTop = WelcomeHead.getInstance().getConfig().getInt("Spaces-Top");
        final int spacesBot = WelcomeHead.getInstance().getConfig().getInt("Spaces-Bot");

        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) WelcomeHead.getInstance(), new Runnable() {
            public void run() {
                if (!player.hasPlayedBefore()) {
                    handleFirstJoin(player, spaceTop, spacesBot);
                } else {
                    handleBackJoin(player, spaceTop, spacesBot);
                }
            }
        }, WelcomeHead.getInstance().getConfig().getInt("Timer") * 20L);
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

        playEffectsAndCommands(player, "SoundA", "Commands-First", "First player");
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

        playEffectsAndCommands(player, "SoundB", "Commands-Back", "Back player");
    }

    private void sendEmptySpaces(Player player, int spaces) {
        for (int i = 0; i <= spaces; i++) {
            player.sendMessage(" ");
        }
    }

    private void sendImageMessage(Player player, String configPath) {
        try {
            BufferedImage imageToSend = ImageIO.read(new URL("https://minotar.net/avatar/" + player.getName() + "/8.png"));
            new ImageMessage(imageToSend, 8, ImageChar.BLOCK.getChar())
                    .appendText(getColoredText(player, configPath))
                    .sendToPlayer(player);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String[] getColoredText(Player player, String configPath) {
        String[] textLines = new String[8];
        for (int i = 1; i <= 8; i++) {
            String line = WelcomeHead.getInstance().getConfig().getString(configPath + "." + i);
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
