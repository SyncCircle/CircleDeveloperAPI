package de.synccircle.circledeveloperapi.service;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.util.Command;
import de.synccircle.circledeveloperapi.util.Configuration;
import org.bukkit.command.PluginCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandService {

    private final CircleDeveloperAPI plugin;

    private final List<Command> commandCache = new ArrayList<>();

    public CommandService(CircleDeveloperAPI plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(Command command) {
        PluginCommand pluginCommand = command.plugin().getServer().getPluginCommand(command.name());
        if(pluginCommand != null) {
            Configuration configuration;
            try {
                configuration = this.plugin.getConfigService().loadPluginConfig(command.plugin().getServer().getPort(), command.plugin().getName(), "commands.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(!configuration.configuration().contains(pluginCommand.getName())) {
                configuration.configuration().set(pluginCommand.getName(), true);
                configuration.save();
            }
            if(configuration.configuration().getBoolean(pluginCommand.getName(), false)) {
                pluginCommand.setExecutor(command.executor());
            } else {
                List<String> commandNames = pluginCommand.getAliases();
                commandNames.add(pluginCommand.getName());
                commandNames.forEach(name -> command.plugin().getServer().getCommandMap().getKnownCommands().remove(name));
            }
            if(!this.commandCache.contains(command)) {
                this.commandCache.add(command);
            }
        }
    }

    public void reloadCommands() {
        for(Command command : this.commandCache) {
            PluginCommand pluginCommand = command.plugin().getServer().getPluginCommand(command.name());
            if(pluginCommand != null) {
                pluginCommand.unregister(command.plugin().getServer().getCommandMap());
            }

            this.registerCommand(command);
        }
    }
}
