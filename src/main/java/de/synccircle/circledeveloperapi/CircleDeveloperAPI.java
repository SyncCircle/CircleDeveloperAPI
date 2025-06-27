package de.synccircle.circledeveloperapi;

import de.synccircle.circledeveloperapi.command.LanguageCommand;
import de.synccircle.circledeveloperapi.config.ConfigService;
import de.synccircle.circledeveloperapi.language.LanguageService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class CircleDeveloperAPI extends JavaPlugin {

    @Getter
    private File storage;

    @Getter
    private ConfigService configService;
    @Getter
    private LanguageService languageService;

    @Override
    public void onEnable() {
        this.createStorage();

        this.configService = new ConfigService(this);
        this.languageService = new LanguageService(this);

        this.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        this.getCommand("language").setExecutor(new LanguageCommand(this));
    }

    private void createStorage() {
        this.storage = new File("/home/minecraft/.storage/" + this.getServer().getPort());
        if(this.storage.exists()) {
            boolean ignored = this.storage.mkdirs();
        }
    }

    public static CircleDeveloperAPI getInstance() {
        return getPlugin(CircleDeveloperAPI.class);
    }
}
