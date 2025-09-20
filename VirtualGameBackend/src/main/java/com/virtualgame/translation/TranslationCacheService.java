package com.virtualgame.translation;

import com.virtualgame.translation.translationEntity.TranslationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationCacheService {

    private final TranslationRepository translationRepository;
    private final Map<String, Map<String, String>> translationCache = new ConcurrentHashMap<>();

    @PostConstruct
    @Transactional
    public void init() {
        log.info("Initializing translation cache");
        loadAllTranslations();
    }

    @Transactional
    public void loadAllTranslations() {
        log.info("Loading all translations into cache");
        translationCache.clear();

        translationRepository.findAllWithLanguageAndMessage().forEach(translation -> {
            String languageCode = translation.getLanguage().getCode();
            String messageKey = translation.getMessage().getMessageKey();
            String translatedText = translation.getTranslatedText();

            translationCache
                    .computeIfAbsent(languageCode, k -> new ConcurrentHashMap<>())
                    .put(messageKey, translatedText);
        });

        log.info("Loaded {} languages with translations into cache", translationCache.size());
    }

    public String getTranslation(String languageCode, String messageKey) {
        Map<String, String> languageTranslations = translationCache.get(languageCode);
        if (languageTranslations != null) {
            return languageTranslations.get(messageKey);
        }
        return null;
    }

    public void clearCache() {
        log.info("Clearing translation cache");
        translationCache.clear();
        // No cargamos inmediatamente, se cargará cuando se necesite
    }

    @Transactional
    public void reloadCache() {
        log.info("Reloading translation cache");
        translationCache.clear();
        loadAllTranslations();
    }

    public void updateCache(String languageCode, String messageKey, String translatedText) {
        log.debug("Updating cache for language: {}, message: {}", languageCode, messageKey);
        translationCache
                .computeIfAbsent(languageCode, k -> new ConcurrentHashMap<>())
                .put(messageKey, translatedText);
    }

    public void removeFromCache(String languageCode, String messageKey) {
        Map<String, String> languageTranslations = translationCache.get(languageCode);
        if (languageTranslations != null) {
            languageTranslations.remove(messageKey);
            log.debug("Removed from cache: language={}, message={}", languageCode, messageKey);
        }
    }
}