package de.synccircle.circledeveloperapi;

import de.synccircle.circledeveloperapi.command.CircleDeveloperAPICommand;
import de.synccircle.circledeveloperapi.service.CommandService;
import de.synccircle.circledeveloperapi.service.ConfigService;
import de.synccircle.circledeveloperapi.service.MessageService;
import de.synccircle.circledeveloperapi.util.Command;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class CircleDeveloperAPI extends JavaPlugin {

    @Getter
    private LuckPerms luckPerms;

    @Getter
    private ConfigService configService;
    @Getter
    private CommandService commandService;
    @Getter
    private MessageService messageService;

    @Override
    public void onEnable() {
        this.luckPerms = LuckPermsProvider.get();

        this.configService = new ConfigService(this);
        this.commandService = new CommandService(this);
        this.messageService = new MessageService(this);

        this.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        this.messageService.load();

        this.getCommandService().registerCommand(new Command(this, "circledeveloperapi", new CircleDeveloperAPICommand(this)));
    }

    public static CircleDeveloperAPI getInstance() {
        return getPlugin(CircleDeveloperAPI.class);
    }
}
