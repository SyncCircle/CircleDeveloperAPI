package de.synccircle.circledeveloperapi.config.language;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum LanguageEN {

    PREFIX("&a&lCircle §8» "),
    NO_PERMISSION("&cYou haven't permission!"),
    ERROR("&4ERROR"),
    LINE("&8&m---------------------------------------"),
    COMMAND_LANGUAGE_INVALID("&7This is not a valid language!"),
    COMMAND_LANGUAGE_CHANGED("&7You have changed the language to &a%language%&7.");

    private final String defaultMessage;

    @Setter
    private String message;

    LanguageEN(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public static String getMessageWithPrefix(LanguageEN message) {
        return PREFIX.getMessage() + message.getMessage();
    }

    public static LanguageEN getByName(String name) {
        return LanguageEN.valueOf(name.toUpperCase());
    }

    @Override
    public String toString() {
        return this.message;
    }
}
