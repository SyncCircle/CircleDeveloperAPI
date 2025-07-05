package de.synccircle.circledeveloperapi;

import de.synccircle.circledeveloperapi.command.CircleDeveloperAPICommand;
import de.synccircle.circledeveloperapi.service.CommandService;
import de.synccircle.circledeveloperapi.service.ConfigService;
import de.synccircle.circledeveloperapi.service.MessageService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class CircleDeveloperAPI extends JavaPlugin {

    @Getter
    private ConfigService configService;
    @Getter
    private CommandService commandService;
    @Getter
    private MessageService messageService;

    @Override
    public void onEnable() {
        this.configService = new ConfigService(this);
        this.commandService = new CommandService(this);
        this.messageService = new MessageService(this);

        this.initConfigs();

        this.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        this.getCommandService().registerCommand(this, "circledeveloperapi", new CircleDeveloperAPICommand(this));
    }

    private void initConfigs() {
        //init message config
        this.getMessageService().load();
    }

    public static CircleDeveloperAPI getInstance() {
        return getPlugin(CircleDeveloperAPI.class);
    }
}
