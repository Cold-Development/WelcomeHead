package org.padrewin.welcomehead.manager;

import com.google.common.collect.Lists;
import org.padrewin.welcomehead.WelcomeHead;
import dev.padrewin.colddev.ColdPlugin;
import dev.padrewin.colddev.config.CommentedFileConfiguration;
import dev.padrewin.colddev.manager.Manager;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.padrewin.welcomehead.commands.Commander;
import org.bukkit.command.PluginCommand;

public class CommandManager extends Manager {

    public enum CommandAliases {
        ROOT {
            @Override
            public List<String> get() {
                return Collections.emptyList();
            }
        },
        RELOAD,
        VERSION,
        HELP;

        private List<String> aliases;

        public List<String> get() {
            return this.aliases;
        }

        private void set(List<String> aliases) {
            this.aliases = aliases.stream().filter(x -> !x.trim().isEmpty()).collect(Collectors.toList());
        }
    }

    public CommandManager(ColdPlugin coldPlugin) {
        super(coldPlugin);
    }

    @Override
    public void reload() {
        File file = new File(this.coldPlugin.getDataFolder(), "aliases.yml");
        CommentedFileConfiguration fileConfiguration = CommentedFileConfiguration.loadConfiguration(file);

        boolean changes = false;
        for (CommandAliases value : CommandAliases.values()) {
            if (value == CommandAliases.ROOT)
                continue;

            String key = value.name().toLowerCase();
            if (fileConfiguration.contains(key)) {
                value.set(fileConfiguration.getStringList(key));
            } else {
                changes = true;
                fileConfiguration.set(key, Lists.newArrayList(key));
                value.set(Collections.singletonList(key));
            }
        }

        if (changes)
            fileConfiguration.save(file);

        // Register commands
        Commander commander = new Commander((WelcomeHead) this.coldPlugin);
        PluginCommand command = this.coldPlugin.getCommand("welcomehead");
        if (command != null)
            command.setExecutor(commander);
    }

    @Override
    public void disable() {

    }

}