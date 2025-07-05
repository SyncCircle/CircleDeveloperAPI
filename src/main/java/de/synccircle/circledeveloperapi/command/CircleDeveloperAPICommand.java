package de.synccircle.circledeveloperapi.command;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.MainMessage;
import de.synccircle.circledeveloperapi.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class CircleDeveloperAPICommand implements CommandExecutor {

    private final CircleDeveloperAPI plugin;

    public CircleDeveloperAPICommand(CircleDeveloperAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!commandSender.hasPermission("circle.command.circledeveloperapi")) return true;

        if(strings.length == 2) {
            if(strings[0].equalsIgnoreCase("reload")) {
                if(commandSender.hasPermission("circle.command.circledeveloperapi.reload")) {
                    //Reload messages
                    if(strings[1].equalsIgnoreCase("messages")) {
                        if(commandSender.hasPermission("circle.command.circledeveloperapi.reload.messages")) {
                            this.plugin.getMessageService().load();
                            commandSender.sendMessage(StringUtil.replacePlaceholder(MainMessage.getMessageWithPrefix(MainMessage.COMMAND_CIRCLEDEVELOPERAPI_RELOAD_CONFIRM), Map.of("%config%", "Messages")));
                            return true;
                        }
                    }
                    //Reload Commands
                    if(strings[1].equalsIgnoreCase("commands")) {
                        if(commandSender.hasPermission("circle.command.circledeveloperapi.reload.commands")) {
                            this.plugin.getCommandService().reloadCommands();
                            commandSender.sendMessage(StringUtil.replacePlaceholder(MainMessage.getMessageWithPrefix(MainMessage.COMMAND_CIRCLEDEVELOPERAPI_RELOAD_CONFIRM), Map.of("%config%", "Commands")));
                            return true;
                        }
                    }
                }
            }
        }
        commandSender.sendMessage(MainMessage.getMessageWithPrefix(MainMessage.COMMAND_CIRCLEDEVELOPERAPI_HELP));
        return true;
    }
}
