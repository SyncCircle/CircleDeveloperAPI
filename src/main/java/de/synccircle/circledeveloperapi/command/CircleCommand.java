package de.synccircle.circledeveloperapi.command;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.APIMessage;
import de.synccircle.circledeveloperapi.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
                            this.plugin.getMessageService().load();
                            commandSender.sendMessage(StringUtil.replacePlaceholder(APIMessage.getMessageWithPrefix(APIMessage.COMMAND_CIRCLE_RELOAD_CONFIRM), Map.of("%config%", "Messages")));
                            return true;
                        }
                    }
                }
            }
        }
        commandSender.sendMessage(APIMessage.getMessageWithPrefix(APIMessage.COMMAND_CIRCLE_HELP));
        return true;
    }
}
