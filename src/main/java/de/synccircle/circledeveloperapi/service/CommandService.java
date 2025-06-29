package de.synccircle.circledeveloperapi.service;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.util.Configuration;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.List;

public class CommandService {

    private final CircleDeveloperAPI plugin;

    public CommandService(CircleDeveloperAPI plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(Plugin commandPlugin, String name, CommandExecutor executor) {
        PluginCommand command = commandPlugin.getServer().getPluginCommand(name);
        if(command != null) {
            Configuration configuration;
            try {
                configuration = this.plugin.getConfigService().loadPluginConfig(commandPlugin.getServer().getPort(), commandPlugin.getName(), "commands.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(!configuration.configuration().contains(command.getName())) {
                configuration.configuration().set(command.getName(), true);
                configuration.save();
            }
            if(configuration.configuration().getBoolean(command.getName(), false)) {
                command.setExecutor(executor);
            } else {
                List<String> commandNames = command.getAliases();
                commandNames.add(command.getName());
                commandNames.forEach(names -> commandPlugin.getServer().getCommandMap().getKnownCommands().remove(names));
            }
        }
    }
}
