package de.synccircle.circledeveloperapi.language;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.synccircle.circledeveloperapi.CircleDeveloperAPI;
import de.synccircle.circledeveloperapi.language.util.Language;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LanguageService {

    private final CircleDeveloperAPI plugin;

    private final Cache<UUID, Language> userLanguages;

    public LanguageService(CircleDeveloperAPI plugin) {
        this.plugin = plugin;

        this.userLanguages = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.MINUTES).build();
    }

    private void cacheUser(UUID uuid) {
        Language language = Language.EN;
        String languageString = this.plugin.getConfigService().getLanguageUsersConfiguration().getString(uuid.toString());
        if(languageString != null) {
            language = Language.getByValue(languageString);
        } else {
            this.plugin.getConfigService().getLanguageUsersConfiguration().set(uuid.toString(), language.getValue());
            this.saveUsersLanguage();
        }

        this.userLanguages.put(uuid, language);
    }

    public void changeLanguage(UUID uuid, Language newLanguage) {
        this.plugin.getConfigService().getLanguageUsersConfiguration().set(uuid.toString(), newLanguage.getValue());
        this.saveUsersLanguage();
        if(this.userLanguages.asMap().containsKey(uuid)) {
            this.userLanguages.asMap().replace(uuid, Language.getByValue(this.plugin.getConfigService().getLanguageUsersConfiguration().getString(uuid.toString())));
        } else {
            this.cacheUser(uuid);
        }
    }

    public Language getUserLanguage(UUID uuid) {
        if(!this.userLanguages.asMap().containsKey(uuid)) {
            this.cacheUser(uuid);
        }
        return this.userLanguages.getIfPresent(uuid);
    }

    private void saveUsersLanguage() {
        try {
            this.plugin.getConfigService().getLanguageUsersConfiguration().save(this.plugin.getConfigService().getLanguageUsersFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
