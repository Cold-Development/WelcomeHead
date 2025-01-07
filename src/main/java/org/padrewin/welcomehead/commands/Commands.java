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

        // Verificăm dacă sender-ul este un jucător
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Verificăm permisiunile
            if (player.isOp() || player.hasPermission("welcomehead.admin")) {
                if (args.length == 0) {
                    player.sendMessage(Utils.translateColors(prefix + "Developed by padrewin!"));
                    return true;
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    // Reîncărcăm configurația plugin-ului
                    WelcomeHead.getInstance().reloadConfig();
                    WelcomeHead.getInstance().saveDefaultConfig();
                    WelcomeHead.getInstance().reloadConfig();

                    // Actualizăm prefixul folosind metoda centralizată de traducere a culorilor
                    prefix = Utils.translateColors(WelcomeHead.getInstance().getConfig().getString("prefix"));
                    WelcomeHead.prefix = prefix;

                    // Trimitem mesajul de confirmare cu suport pentru culori
                    sender.sendMessage(Utils.translateColors(prefix + "Reload completed!"));
                    return true;
                }
            }
        }

        // Dacă sender-ul nu este un jucător sau nu are permisiuni
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.translateColors(prefix + "This command can only be run by a player!"));
        }

        return false;
    }
}
