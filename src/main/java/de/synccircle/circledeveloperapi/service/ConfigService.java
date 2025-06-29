package de.synccircle.circledeveloperapi.service;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.util.Configuration;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigService {

    private final CircleDeveloperAPI plugin;

    @Getter
    private final File storageFolder = new File("/home/minecraft/.storage");

    public ConfigService(CircleDeveloperAPI plugin) {
        this.plugin = plugin;

        this.createFolders();
    }

    private void createFolders() {
        if(!storageFolder.exists()) {
            boolean ignored = storageFolder.mkdirs();
        }
    }

    //To create/Load a config /server folder, plugin name and file name/
    public Configuration loadPluginConfig(int port, String pluginName, String fileName) throws IOException {
        File folders = new File(this.storageFolder.getPath() + "/" + port + "/" + pluginName);
        if(!folders.exists()) {
            boolean ignored = folders.mkdirs();
        }
        File file = new File(folders, fileName);
        if(file.exists()) {
            boolean ignored = file.createNewFile();
        }

        return new Configuration(file, YamlConfiguration.loadConfiguration(file));
    }

    //To create/load a message file for a plugin
    public Configuration loadMessageConfig(String pluginName) throws IOException {
        File folders = new File(this.storageFolder.getPath() + "/messages/" + pluginName);
        if(!folders.exists()) {
            boolean ignored = folders.mkdirs();
        }
        File file = new File(folders, "config.yml");
        if(file.exists()) {
            boolean ignored = file.createNewFile();
        }

        return new Configuration(file, YamlConfiguration.loadConfiguration(file));
    }

    //To create/load a custom config
    public Configuration loadConfig(String pathName, String fileName) throws IOException {
        File folders = new File(this.storageFolder.getPath() + pathName);
        if(!folders.exists()) {
            boolean ignored = folders.mkdirs();
        }
        File file = new File(folders, fileName);
        if(file.exists()) {
            boolean ignored = file.createNewFile();
        }

        return new Configuration(file, YamlConfiguration.loadConfiguration(file));
    }
}
