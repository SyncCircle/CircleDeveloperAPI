package de.synccircle.circledeveloperapi.command;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.language.LanguageTemplate;
import de.synccircle.circledeveloperapi.language.util.Language;
import de.synccircle.circledeveloperapi.message.Message;
import de.synccircle.circledeveloperapi.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class LanguageCommand implements CommandExecutor {

    private final CircleDeveloperAPI plugin;

    public LanguageCommand(CircleDeveloperAPI plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)) return true;
        if(!player.hasPermission("circle.command.language")) return true;

        if(strings.length == 1) {
            if(strings[0].equalsIgnoreCase("en") || strings[0].equalsIgnoreCase("english") || strings[0].equalsIgnoreCase("englisch")) {
                this.plugin.getLanguageService().changeLanguage(player.getUniqueId(), Language.EN);
                player.sendMessage(StringUtil.replacePlaceholder(Message.getMessageWithPrefix(player.getUniqueId(), LanguageTemplate.COMMAND_LANGUAGE_CHANGED), Map.of("%language%", "ENGLISH")));
                return true;
            }
            if(strings[0].equalsIgnoreCase("de") || strings[0].equalsIgnoreCase("german") || strings[0].equalsIgnoreCase("deutsch")) {
                this.plugin.getLanguageService().changeLanguage(player.getUniqueId(), Language.DE);
                player.sendMessage(StringUtil.replacePlaceholder(Message.getMessageWithPrefix(player.getUniqueId(), LanguageTemplate.COMMAND_LANGUAGE_CHANGED), Map.of("%language%", "DEUTSCH")));
                return true;
            }
            return true;
        }
        return true;
    }
}
