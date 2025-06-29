package de.synccircle.circledeveloperapi.config;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Message {

    PREFIX("&a&lCircle §8» "),
    NO_PERMISSION("&cDazu hast du keine Rechte!"),
    ERROR("&4ERROR"),
    LINE("&8&m---------------------------------------");

    private final String defaultMessage;

    @Setter
    private String message;

    Message(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public static String getMessageWithPrefix(Message message) {
        return PREFIX.getMessage() + message.getMessage();
    }

    @Override
    public String toString() {
        return this.message;
    }
}
