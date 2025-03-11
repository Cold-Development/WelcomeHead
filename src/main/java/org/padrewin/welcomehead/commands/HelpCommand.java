package org.padrewin.welcomehead.commands;

import java.util.Collections;
import java.util.List;
import org.padrewin.welcomehead.WelcomeHead;
import org.padrewin.welcomehead.manager.CommandManager;
import org.padrewin.welcomehead.manager.LocaleManager;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

public class HelpCommand extends BaseCommand {

    private final CommandHandler commandHandler;

    public HelpCommand(CommandHandler commandHandler) {
        super("help", CommandManager.CommandAliases.HELP);
        this.commandHandler = commandHandler;
    }

    @Override
    public void execute(WelcomeHead plugin, CommandSender sender, String[] args) {
        LocaleManager localeManager = plugin.getManager(LocaleManager.class);

        // Send header
        localeManager.sendMessage(sender, "command-help-title");

        for (NamedExecutor executor : this.commandHandler.getExecutables())
            if (executor.hasPermission(sender))
                localeManager.sendSimpleMessage(sender, "command-" + executor.getName() + "-description");
    }

    @Override
    public List<String> tabComplete(WelcomeHead plugin, CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    @Override
    public boolean hasPermission(Permissible permissible) {
        return true;
    }

}