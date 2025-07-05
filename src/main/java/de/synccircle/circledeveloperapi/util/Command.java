package de.synccircle.circledeveloperapi.util;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

@Getter
public class Command {

    private final Plugin plugin;
    private final String name;
    private final CommandExecutor executor;
    @Setter
    private String permission;

    public Command(Plugin plugin, String name, CommandExecutor executor) {
        this.plugin = plugin;
        this.name = name;
        this.executor = executor;
    }
}
