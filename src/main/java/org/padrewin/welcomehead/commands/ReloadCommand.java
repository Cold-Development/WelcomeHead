package org.padrewin.welcomehead.commands;

import java.util.Collections;
import java.util.List;
import org.padrewin.welcomehead.WelcomeHead;
import org.padrewin.welcomehead.manager.CommandManager;
import org.padrewin.welcomehead.manager.LocaleManager;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand() {
        super("reload", CommandManager.CommandAliases.RELOAD);
    }

    @Override
    public void execute(WelcomeHead plugin, CommandSender sender, String[] args) {
        if (!sender.hasPermission("welcomehead.reload")) {
            plugin.getManager(LocaleManager.class).sendMessage(sender, "no-permission");
            return;
        }

        if (args.length > 0) {
            plugin.getManager(LocaleManager.class).sendMessage(sender, "command-reload-usage");
            return;
        }

        plugin.reloadConfig();
        plugin.reload();
        plugin.getManager(LocaleManager.class).sendMessage(sender, "command-reload-success");
    }

    @Override
    public List<String> tabComplete(WelcomeHead plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
