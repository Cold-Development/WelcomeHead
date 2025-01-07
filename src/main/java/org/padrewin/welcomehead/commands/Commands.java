package org.padrewin.welcomehead.commands;

import org.padrewin.welcomehead.Utils.Utils;
import org.padrewin.welcomehead.WelcomeHead;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = WelcomeHead.prefix;

        // Dacă comanda este executată de un jucător
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isOp() || player.hasPermission("welcomehead.admin")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.translateColors(prefix + "Developed by padrewin!"));
                    return true;
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    WelcomeHead.getInstance().reloadConfig();
                    WelcomeHead.getInstance().saveDefaultConfig();
                    WelcomeHead.getInstance().reloadConfig();

                    prefix = Utils.translateColors(WelcomeHead.getInstance().getConfig().getString("prefix"));
                    WelcomeHead.prefix = prefix;

                    player.sendMessage(Utils.translateColors(prefix + "Reload completed!"));
                    return true;
                }
            }
        } else {
            // Dacă comanda este executată din consolă
            if (args.length == 0) {
                sender.sendMessage(Utils.translateColors(prefix + "Developed by padrewin!"));
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                WelcomeHead.getInstance().reloadConfig();
                WelcomeHead.getInstance().saveDefaultConfig();
                WelcomeHead.getInstance().reloadConfig();

                prefix = Utils.translateColors(WelcomeHead.getInstance().getConfig().getString("prefix"));
                WelcomeHead.prefix = prefix;

                sender.sendMessage(Utils.translateColors(prefix + "Reload completed from console!"));
                return true;
            }
        }

        sender.sendMessage(Utils.translateColors(prefix + "You do not have permission to run this command!"));
        return false;
    }

}
