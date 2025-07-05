package de.synccircle.circledeveloperapi.config;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum MainMessage {

    PREFIX("&a&lCircle §8» "),
    NO_PERMISSION("&cDazu hast du keine Rechte!"),
    ERROR("&4ERROR"),
    LINE("&8&m---------------------------------------"),

    COMMAND_CIRCLE_HELP("&7Nutze: /circle reload messages"),
    COMMAND_CIRCLE_RELOAD_CONFIRM("&7Die Konfiguration für &a%config% &7wurde neu gelauden.");

    private final String defaultMessage;

    @Setter
    private String message;

    MainMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public static String getMessageWithPrefix(MainMessage message) {
        return PREFIX.getMessage() + message.getMessage();
    }

    @Override
    public String toString() {
        return this.message;
    }
}
