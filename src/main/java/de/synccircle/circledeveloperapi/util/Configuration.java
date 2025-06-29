package de.synccircle.circledeveloperapi.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public record Configuration(File file, YamlConfiguration configuration) {

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
