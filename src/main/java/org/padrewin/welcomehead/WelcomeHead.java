package org.padrewin.welcomehead;

import dev.padrewin.colddev.ColdPlugin;
import dev.padrewin.colddev.config.ColdSetting;
import dev.padrewin.colddev.database.DatabaseConnector;
import dev.padrewin.colddev.database.SQLiteConnector;
import dev.padrewin.colddev.manager.Manager;
import dev.padrewin.colddev.manager.PluginUpdateManager;
import org.padrewin.welcomehead.database.DatabaseManager;
import org.padrewin.welcomehead.listener.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.padrewin.welcomehead.manager.CommandManager;
import org.padrewin.welcomehead.manager.LocaleManager;
import org.padrewin.welcomehead.settings.SettingKey;

import java.io.File;
import java.util.List;

public final class WelcomeHead extends ColdPlugin {

  /**
   * Console colors
   */
  String ANSI_RESET = "\u001B[0m";
  String ANSI_CHINESE_PURPLE = "\u001B[38;5;93m";
  String ANSI_PURPLE = "\u001B[35m";
  String ANSI_GREEN = "\u001B[32m";
  String ANSI_RED = "\u001B[31m";
  String ANSI_AQUA = "\u001B[36m";
  String ANSI_PINK = "\u001B[35m";
  String ANSI_YELLOW = "\u001B[33m";


  public static WelcomeHead instance;

  public WelcomeHead() {
    super("Cold-Development", "WelcomeHead", 25064, null, LocaleManager.class, null);
    instance = this;
  }
  private DatabaseManager databaseManager;

  public void enable() {
    instance = this;

    // Initialize DatabaseManager
    databaseManager = new DatabaseManager(this, "welcomehead.db");
    DatabaseConnector connector = new SQLiteConnector(this);
    String databasePath = connector.getDatabasePath();
    getLogger().info(ANSI_GREEN + "Database path: " + ANSI_YELLOW + databasePath + ANSI_RESET);

if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

  getManager(PluginUpdateManager.class);

  String name = getDescription().getName();
  getLogger().info("");
  getLogger().info(ANSI_CHINESE_PURPLE + "  ____ ___  _     ____  " + ANSI_RESET);
  getLogger().info(ANSI_PINK + " / ___/ _ \\| |   |  _ \\ " + ANSI_RESET);
  getLogger().info(ANSI_CHINESE_PURPLE + "| |  | | | | |   | | | |" + ANSI_RESET);
  getLogger().info(ANSI_PINK + "| |__| |_| | |___| |_| |" + ANSI_RESET);
  getLogger().info(ANSI_CHINESE_PURPLE + " \\____\\___/|_____|____/ " + ANSI_RESET);
  getLogger().info("    " + ANSI_GREEN + name + ANSI_RED + " v" + getDescription().getVersion() + ANSI_RESET);
  getLogger().info(ANSI_PURPLE + "    Author(s): " + ANSI_PURPLE + getDescription().getAuthors().get(0) + ANSI_RESET);
  getLogger().info(ANSI_AQUA + "    (c) Cold Development ❄" + ANSI_RESET);
  getLogger().info("");


  File configFile = new File(getDataFolder(), "en_US.yml");
  if (!configFile.exists()) {
    saveDefaultConfig();
  }

  saveDefaultConfig();

  Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);

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

  public void disable() {
    if (databaseManager != null) {
      databaseManager.closeConnection();
    }
    getLogger().info("");
    getLogger().info(ANSI_CHINESE_PURPLE + "WelcomeHead disabled." + ANSI_RESET);
    getLogger().info("");
    }

  @Override
  public void reload() {
    super.reload();
  }

  @Override
  protected List<Class<? extends Manager>> getManagerLoadPriority() {
    return List.of(
            CommandManager.class
    );
  }

  @Override
  protected List<ColdSetting<?>> getColdConfigSettings() {
    return SettingKey.getKeys();
  }

  @Override
  protected String[] getColdConfigHeader() {
    return new String[] {
            " ██████╗ ██████╗ ██╗     ██████╗ ",
            "██╔════╝██╔═══██╗██║     ██╔══██╗",
            "██║     ██║   ██║██║     ██║  ██║",
            "██║     ██║   ██║██║     ██║  ██║",
            "╚██████╗╚██████╔╝███████╗██████╔╝",
            " ╚═════╝ ╚═════╝ ╚══════╝╚═════╝ ",
            "                                 "
    };
  }

  public DatabaseManager getDatabaseManager() {
    return databaseManager;
  }

  public static WelcomeHead getInstance() {
    if (instance == null) {
      throw new IllegalStateException("WelcomeHead instance is not initialized!");
    }
    return instance;
  }

}