package de.synccircle.circledeveloperapi.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public record Configuration(File file, YamlConfiguration configuration) {
}
