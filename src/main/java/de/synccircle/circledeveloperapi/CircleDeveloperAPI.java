package de.synccircle.circledeveloperapi;

import de.synccircle.circledeveloperapi.config.Message;
import de.synccircle.circledeveloperapi.service.ConfigService;
import de.synccircle.circledeveloperapi.util.Configuration;
import de.synccircle.circledeveloperapi.util.StringUtil;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class CircleDeveloperAPI extends JavaPlugin {

    @Getter
    private ConfigService configService;

    @Override
    public void onEnable() {
        this.configService = new ConfigService(this);

        this.initConfigs();

        this.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        Command command = this.getCommand("test");
        if(command.getName().equalsIgnoreCase("test")) {

        }
    }

    private void initConfigs() {
        //init message config
        for(Message message : Message.values()) {
            Configuration configuration;
            try {
                configuration = this.getConfigService().loadMessageConfig(this.getName());
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
    }

    public static CircleDeveloperAPI getInstance() {
        return getPlugin(CircleDeveloperAPI.class);
    }
}
