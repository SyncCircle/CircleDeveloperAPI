package de.synccircle.circledeveloperapi.service;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.util.Command;
import de.synccircle.circledeveloperapi.util.Configuration;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.command.PluginCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CommandService {

    private final CircleDeveloperAPI plugin;

    private final List<Command> commandCache = new ArrayList<>();

    public CommandService(CircleDeveloperAPI plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(Command command) {
        PluginCommand pluginCommand = command.getPlugin().getServer().getPluginCommand(command.getName());
        if(pluginCommand != null) {
            command.setPermission(pluginCommand.getPermission());
            pluginCommand.setExecutor(command.getExecutor());

            if(!this.commandCache.contains(command)) {
                this.commandCache.add(command);
            }

            Configuration configuration;
            try {
                configuration = this.plugin.getConfigService().loadPluginConfig(command.getPlugin().getServer().getPort(), command.getPlugin().getName(), "commands.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(!configuration.configuration().contains(command.getName())) {
                configuration.configuration().set(command.getName(), true);
                configuration.save();
            }

            this.handle(command, configuration, command.getPermission());
        }
    }

    public void reloadCommands() {
        for(Command command : this.commandCache) {
            Configuration configuration;
            try {
                configuration = this.plugin.getConfigService().loadPluginConfig(command.getPlugin().getServer().getPort(), command.getPlugin().getName(), "commands.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.handle(command, configuration, command.getPermission());
        }
    }

    private void handle(Command command, Configuration configuration, String permission) {
        Group defaultGroup = this.plugin.getLuckPerms().getGroupManager().getGroup("default");
        if(defaultGroup != null) {
            if(!configuration.configuration().getBoolean(command.getName(), false)) {
                defaultGroup.data().add(PermissionNode.builder().permission(permission).value(false).build());
            } else {
                defaultGroup.data().remove(PermissionNode.builder().permission(permission).build());
            }
        } else {
            this.plugin.getLogger().log(Level.SEVERE, "The " + command.getName() + " command could not be disabled. No group named \"default\" was found.");
        }
    }
}
