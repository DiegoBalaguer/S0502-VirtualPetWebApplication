package com.virtualgame.translation;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.translation.translationEntity.TranslationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationManagerService {

    private final TranslationCacheService translationCacheService;
    private final TranslationRepository translationRepository;
    private final AppProperties appProperties;
    private String langSys;
    private String langUser;

    @PostConstruct
    public void init() {
        this.langSys = appProperties.getDefaultLanguageSystem();
        this.langUser = appProperties.getDefaultLanguageUser();
    }

    // getFormatedTranslationSystem
    public String getSys(String messageKey, Object... args) {
        return getFormattedTranslation(langSys, messageKey, args);
    }

    // getFormatedTranslationUser
    public String getUsr(String userLanguageCode, String messageKey, Object... args) {
/*
        Long userLanguageId = userRepository.findById(userId).get().getLanguageId();

        String codeLang = languageRepository.findById(userLanguageId).get().getCode();
*/
        //String languageCode = currentUserService.getCurrentUserLanguageCode();

        String langUserId = (userLanguageCode.isEmpty()) ? langUser : userLanguageCode;

        return getFormattedTranslation(langUserId, messageKey, args);
    }

    public String getFormattedTranslation(String languageCode, String messageKey, Object... args) {
        String translation = getTranslation(languageCode, messageKey);
        return MessageFormat.format(translation, args);
    }

    public String getTranslationClear(String languageCode, String messageKey) {

        return getTranslation(languageCode, messageKey).replaceAll("\\{\\d+\\}", "{}");
    }


    public String getTranslation(String languageCode, String messageKey) {
        log.debug("Getting translation for language: {}, message: {}", languageCode, messageKey);

        String translation = translationCacheService.getTranslation(languageCode, messageKey);

        if (translation != null && !translation.isEmpty()) {
            return translation;
        }

        log.debug("Translation not found in cache, return default value");
        if (appProperties.getLanguageTranslateWithDefault()) return messageKey;
        log.debug("Translation not found in cache, checking database");
        return translationRepository.findTranslatedTextByLanguageCodeAndMessageKey(languageCode, messageKey)
                .orElse(messageKey);
    }

    public String getTranslation(String languageCode, String messageKey, String defaultValue) {
        try {
            return getTranslation(languageCode, messageKey);
        } catch (RuntimeException e) {
            log.warn("Using default value for language: {}, message: {}", languageCode, messageKey);
            return defaultValue;
        }
    }
}