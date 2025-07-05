package de.synccircle.circledeveloperapi.service;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.MainMessage;
import de.synccircle.circledeveloperapi.util.Configuration;
import de.synccircle.circledeveloperapi.util.StringUtil;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class MessageService {

    private final Plugin plugin;

    public MessageService(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        for(MainMessage mainMessage : MainMessage.values()) {
            Configuration configuration;
            try {
                configuration = CircleDeveloperAPI.getInstance().getConfigService().loadMessageConfig(this.plugin.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(configuration.configuration().getString(mainMessage.name().toLowerCase()) == null) {
                configuration.configuration().set(mainMessage.name().toLowerCase(), mainMessage.getDefaultMessage());
                try {
                    configuration.configuration().save(configuration.file());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                mainMessage.setMessage(StringUtil.colorizeString(mainMessage.getDefaultMessage()));
                continue;
            }
            mainMessage.setMessage(StringUtil.colorizeString(configuration.configuration().getString(mainMessage.name().toLowerCase())));
        }
    }
}
