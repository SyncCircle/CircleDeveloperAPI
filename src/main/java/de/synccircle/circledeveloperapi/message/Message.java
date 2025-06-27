package de.synccircle.circledeveloperapi.message;

import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.config.language.LanguageDE;
import de.synccircle.circledeveloperapi.config.language.LanguageEN;
import de.synccircle.circledeveloperapi.config.language.LanguageTemplate;
import de.synccircle.circledeveloperapi.language.util.Language;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Message {
    public static String getMessageWithPrefix(UUID uuid, LanguageTemplate template) {
        String message;
        switch (CircleDeveloperAPI.getInstance().getLanguageService().getUserLanguage(uuid)) {
            case EN: {
                message = LanguageEN.getMessageWithPrefix(LanguageEN.valueOf(template.name().toUpperCase()));
                break;
            }
            case DE: {
                message = LanguageDE.getMessageWithPrefix(LanguageDE.valueOf(template.name().toUpperCase()));
                break;
            }
            default: {
                message = LanguageEN.getMessageWithPrefix(LanguageEN.valueOf(template.name().toUpperCase()));
            }
        }
        return message;
    }

    public static String getMessageWithPrefix(@NotNull Language language, LanguageTemplate template) {
        String message;
        switch (language) {
            case EN: {
                message = LanguageEN.getMessageWithPrefix(LanguageEN.valueOf(template.name().toUpperCase()));
                break;
            }
            case DE: {
                message = LanguageDE.getMessageWithPrefix(LanguageDE.valueOf(template.name().toUpperCase()));
                break;
            }
            default: {
                message = LanguageEN.getMessageWithPrefix(LanguageEN.valueOf(template.name().toUpperCase()));
            }
        }
        return message;
    }
}
