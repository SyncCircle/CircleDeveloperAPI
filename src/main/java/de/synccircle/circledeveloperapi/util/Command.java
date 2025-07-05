package de.synccircle.circledeveloperapi.util;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

public record Command(Plugin plugin, String name, CommandExecutor executor) {}
