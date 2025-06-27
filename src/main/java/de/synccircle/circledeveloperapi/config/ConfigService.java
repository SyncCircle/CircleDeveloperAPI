package de.synccircle.circledeveloperapi.config;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.language.LanguageDE;
import de.synccircle.circledeveloperapi.config.language.LanguageEN;
import de.synccircle.circledeveloperapi.util.StringUtil;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ConfigService {

    private final CircleDeveloperAPI plugin;

    @Getter
    private final File storageFolder = new File("/home/minecraft/.storage");
    @Getter
    private final File languageFolder = new File("/home/minecraft/.storage/language");
    @Getter
    private final File languageUsersFolder = new File("/home/minecraft/.storage/language/users");

    @Getter
    private final File languageUsersFile = new File(this.languageUsersFolder, "data.yml");
    @Getter
    private YamlConfiguration languageUsersConfiguration;
    @Getter
    private final File languageDEFile = new File(this.languageFolder, "language_de.yml");
    @Getter
    private YamlConfiguration languageDEConfiguration;
    @Getter
    private final File languageENFile = new File(this.languageFolder, "language_en.yml");
    @Getter
    private YamlConfiguration languageENConfiguration;

    public ConfigService(CircleDeveloperAPI plugin) {
        this.plugin = plugin;

        this.createFolders();
        try {
            this.createFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.initConfigs();
    }

    private void createFolders() {
        if(!storageFolder.exists()) {
            boolean ignored = storageFolder.mkdirs();
        }
        if(!languageFolder.exists()) {
            boolean ignored = languageFolder.mkdirs();
        }
        if(!languageUsersFolder.exists()) {
            boolean ignored = languageUsersFolder.mkdirs();
        }
    }

    private void createFiles() throws IOException {
        if(!languageUsersFile.exists()) {
            boolean ignored = languageUsersFile.createNewFile();
        }
        if(!languageDEFile.exists()) {
            boolean ignored = languageDEFile.createNewFile();
        }
        if(!languageENFile.exists()) {
            boolean ignored = languageENFile.createNewFile();
        }
    }

    private void initConfigs() {
        this.languageUsersConfiguration = YamlConfiguration.loadConfiguration(this.languageUsersFile);
        this.languageENConfiguration = YamlConfiguration.loadConfiguration(this.languageENFile);
        this.languageDEConfiguration = YamlConfiguration.loadConfiguration(this.languageDEFile);

        //Getting messages in english
        for(LanguageEN message : LanguageEN.values()) {
            if(this.languageENConfiguration.getString(message.name().toLowerCase()) == null) {
                this.languageENConfiguration.set(message.name().toLowerCase(), message.getDefaultMessage());
                try {
                    this.languageENConfiguration.save(this.languageENFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message.setMessage(StringUtil.colorizeString(message.getDefaultMessage()));
                continue;
            }
            message.setMessage(StringUtil.colorizeString(this.languageENConfiguration.getString(message.name().toLowerCase())));
        }
        //Getting messages in german
        for(LanguageDE message : LanguageDE.values()) {
            if(this.languageDEConfiguration.getString(message.name().toLowerCase()) == null) {
                this.languageDEConfiguration.set(message.name().toLowerCase(), message.getDefaultMessage());
                try {
                    this.languageDEConfiguration.save(this.languageDEFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message.setMessage(StringUtil.colorizeString(message.getDefaultMessage()));
                continue;
            }
            message.setMessage(StringUtil.colorizeString(this.languageDEConfiguration.getString(message.name().toLowerCase())));
        }
    }
}
