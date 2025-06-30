package de.synccircle.circledeveloperapi.service;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.APIMessage;
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
        for(APIMessage apiMessage : APIMessage.values()) {
            Configuration configuration;
            try {
                configuration = CircleDeveloperAPI.getInstance().getConfigService().loadMessageConfig(this.plugin.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(configuration.configuration().getString(apiMessage.name().toLowerCase()) == null) {
                configuration.configuration().set(apiMessage.name().toLowerCase(), apiMessage.getDefaultMessage());
                try {
                    configuration.configuration().save(configuration.file());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                apiMessage.setMessage(StringUtil.colorizeString(apiMessage.getDefaultMessage()));
                continue;
            }
            apiMessage.setMessage(StringUtil.colorizeString(configuration.configuration().getString(apiMessage.name().toLowerCase())));
        }
    }
}
