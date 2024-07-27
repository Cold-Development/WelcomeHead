package org.padrewin.welcomehead;

import org.padrewin.welcomehead.Utils.Utils;
import org.padrewin.welcomehead.commands.Commands;
import org.padrewin.welcomehead.listener.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class WelcomeHead extends JavaPlugin {
  public static WelcomeHead instance;
  public static String prefix;

  public WelcomeHead() {
instance = this;
  }


  public static WelcomeHead getInstance() {
return instance;
  }

  public void onEnable() {
if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {


  getLogger().info("------------------------------");
  getLogger().info(" ");
  getLogger().info("-------- WelcomeHead ---------");
  getLogger().info("PlaceHolderApi : OK");
  getLogger().info("Version : " + Bukkit.getVersion());
  getLogger().info(" ");
  getLogger().info("Starting WelcomeHead...");
  getLogger().info(" ");
  getLogger().info("------------------------------");


  saveDefaultConfig();

  getCommand("welcomehead").setExecutor((CommandExecutor)new Commands());

  Bukkit.getPluginManager().registerEvents((Listener)new JoinEvent(), (Plugin)this);
  prefix = Utils.translateHexColorCodes("&#", "", getConfig().getString("prefix") + " ");

    }
    else {

  getLogger().info("-------- WelcomeHead ---------");
  getLogger().severe("PlaceHolderApi : NOT OK");
  getLogger().severe("Version : " + Bukkit.getVersion());
  getLogger().info(" ");
  getLogger().severe("Please download PlaceholderAP or use the right version : 1.16+");
  getLogger().info(" ");
  getLogger().severe("Disabling the plugin...");
  getLogger().info(" ");
  getLogger().info("------------------------------");
  Bukkit.getPluginManager().disablePlugin((Plugin)this);
    }
  }

  public void onDisable() {
getLogger().info("-------- WelcomeHead ---------");
getLogger().info(" ");
getLogger().info("Disabling the plugin...");
getLogger().info(" ");
getLogger().info("------------------------------");
  }
}