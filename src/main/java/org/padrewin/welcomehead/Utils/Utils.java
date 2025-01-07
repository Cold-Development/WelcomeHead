package org.padrewin.welcomehead.Utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.padrewin.welcomehead.WelcomeHead;

public class Utils {
  public static String translateColors(String message) {
    // 1. Procesăm mai întâi culorile HEX
    Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
    Matcher matcher = hexPattern.matcher(message);
    StringBuffer buffer = new StringBuffer(message.length() + 32);
    while (matcher.find()) {
      String group = matcher.group(1);
      matcher.appendReplacement(buffer, "§x§" + group
              .charAt(0) + '§' + group.charAt(1) + '§' + group
              .charAt(2) + '§' + group.charAt(3) + '§' + group
              .charAt(4) + '§' + group.charAt(5));
    }
    message = matcher.appendTail(buffer).toString();

    // 2. Procesăm culorile clasice de Minecraft (& + cod)
    return ChatColor.translateAlternateColorCodes('&', message);
  }


  public static void spawnFireworks(Location location, int amount) {
    if (WelcomeHead.getInstance().getConfig().getInt("Firework.amount") != 0) {
      Location loc = location;
      Firework fw = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
      FireworkMeta fwm = fw.getFireworkMeta();
      fwm.setPower(2);
      fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
      fw.setFireworkMeta(fwm);
      fw.detonate();
      for (int i = 0; i < amount; i++) {
        Firework fw2 = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        fw2.setFireworkMeta(fwm);
      }
    }
  }

  public static void commandsActivated(Player player, List<String> path, String errorMsg) {
    try {
      for (String msg : path)
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), PlaceholderAPI.setPlaceholders(player, msg));
    } catch (Exception a) {
      WelcomeHead.getInstance().getLogger().severe(errorMsg);
      a.getStackTrace();
    }
  }

  public static void headNotActivated(Player player, List<String> path, boolean center) {
    for (String msg : path) {
      if (center) {
        CenterText.sendCenteredMessage(player, msg);
        continue;
      }
      PlaceholderAPI.setPlaceholders(player, translateColors(msg));
    }
  }

  public static void soundActivated(Player player, boolean enable, String sound, double volume, double pitch) {
    if (enable)
      player.playSound(player.getLocation(), Sound.valueOf(sound), (float)volume, (float)pitch);
  }
}