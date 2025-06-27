package de.synccircle.circledeveloperapi.language.util;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
public enum Language {

    DE("de"),
    EN("en");

    private final String value;

    private static final Map<String, Language> BY_ID = Maps.newHashMap();

    static {
        for(Language language : values()) {
            BY_ID.put(language.getValue(), language);
        }
    }

    Language(String value) {
        this.value = value;
    }

    public static @NotNull Language getByValue(String value) {
        return BY_ID.get(value);
    }
}
