package de.synccircle.circledeveloperapi.config.language;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum LanguageDE {

    PREFIX("&a&lCircle §8» "),
    NO_PERMISSION("&cDazu hast du keine Rechte!"),
    ERROR("&4ERROR"),
    LINE("&8&m---------------------------------------"),
    COMMAND_LANGUAGE_INVALID("&7Dies ist keine gültige Sprache!"),
    COMMAND_LANGUAGE_CHANGED("&7Du hast die Sprache auf &a%language% &7geändert.");

    private final String defaultMessage;

    @Setter
    private String message;

    LanguageDE(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public static String getMessageWithPrefix(LanguageDE message) {
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
