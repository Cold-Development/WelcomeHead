package org.padrewin.welcomehead.commands;

import org.padrewin.welcomehead.Utils.Utils;
import org.padrewin.welcomehead.WelcomeHead;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      WelcomeHead.getInstance(); String prefix = WelcomeHead.prefix;

if (sender instanceof Player) {Player player = (Player)sender;
if (sender.isOp() || sender.hasPermission("welcomehead.admin")) {
    if (args.length == 0) {

sender.sendMessage(prefix + "Developed by padrewin!");
return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {

            WelcomeHead.getInstance().reloadConfig();
            WelcomeHead.getInstance().saveDefaultConfig();
            WelcomeHead.getInstance().reloadConfig();
            prefix = Utils.translateHexColorCodes("&#", "",
        WelcomeHead.getInstance().getConfig().getString("prefix"));

                sender.sendMessage(Utils.translateHexColorCodes("&#", "",
      ChatColor.translateAlternateColorCodes('&', WelcomeHead.getInstance().getConfig().getString("prefix") + " ") + "Reload completed!"));
    return true;
        }
      }
    }

    if (!(sender instanceof Player));

    return false;
  }
}
