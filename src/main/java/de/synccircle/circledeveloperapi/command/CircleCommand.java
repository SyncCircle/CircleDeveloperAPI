package de.synccircle.circledeveloperapi.command;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.Message;
import de.synccircle.circledeveloperapi.util.Configuration;
import de.synccircle.circledeveloperapi.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CircleCommand implements CommandExecutor {

    private final CircleDeveloperAPI plugin;

    public CircleCommand(CircleDeveloperAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!commandSender.hasPermission("circle.command.circle")) return true;

        if(strings.length == 2) {
            if(strings[0].equalsIgnoreCase("reload")) {
                if(commandSender.hasPermission("circle.command.circle.reload")) {
                    if(strings[1].equalsIgnoreCase("messages")) {
                        if(commandSender.hasPermission("circle.command.circle.reload.messages")) {
                            for(Message message : Message.values()) {
                                Configuration configuration;
                                try {
                                    configuration = this.plugin.getConfigService().loadMessageConfig(this.plugin.getName());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if(configuration.configuration().getString(message.name().toLowerCase()) == null) {
                                    configuration.configuration().set(message.name().toLowerCase(), message.getDefaultMessage());
                                    try {
                                        configuration.configuration().save(configuration.file());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    message.setMessage(StringUtil.colorizeString(message.getDefaultMessage()));
                                    continue;
                                }
                                message.setMessage(StringUtil.colorizeString(configuration.configuration().getString(message.name().toLowerCase())));
                            }
                            commandSender.sendMessage(Message.getMessageWithPrefix(Message.COMMAND_CIRCLE_RELOAD_CONFIRM));
                            return true;
                        }
                    }
                }
            }
        }
        commandSender.sendMessage("ne");
        return true;
    }
}
