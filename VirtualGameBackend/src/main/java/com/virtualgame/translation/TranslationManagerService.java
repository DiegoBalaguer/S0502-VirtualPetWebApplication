package com.virtualgame.translation;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.userEntity.UserServiceImpl;
import com.virtualgame.translation.languageEntity.LanguageRepository;
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
    private final UserServiceImpl userEntityService;
    private final LanguageRepository languageRepository;
    private final AppProperties appPrp;
    private String langSys;
    private String langUser;

    @PostConstruct
    public void init() {
        this.langSys = appPrp.getDefaultLanguageSystem();
        this.langUser = appPrp.getDefaultLanguageUser();
    }

    // getFormatedTranslationSystem
    public String getFormatSys(String messageKey, Object... args) {
        return getFormattedTranslation(langSys, messageKey, args);
    }

    // getFormatedTranslationUser
    public String getFormatUsr(String messageKey, Long userId, Object... args) {

        Long userLanguageId = userEntityService.findUserEntityById(userId).languageId();
        String codeLang = languageRepository.findById(userLanguageId).get().getCode();

        String langUserId = (codeLang.isEmpty()) ? langUser : codeLang;

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