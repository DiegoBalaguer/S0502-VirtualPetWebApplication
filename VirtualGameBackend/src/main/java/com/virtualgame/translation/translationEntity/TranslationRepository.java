package com.virtualgame.translation.translationEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<TranslationEntity, Long> {

    @Query("SELECT t FROM TranslationEntity t JOIN FETCH t.language JOIN FETCH t.message WHERE t.deletedAt IS NULL")
    List<TranslationEntity> findAllWithLanguageAndMessage();

    @Query("SELECT t FROM TranslationEntity t JOIN FETCH t.language l JOIN FETCH t.message m WHERE l.code = :languageCode AND m.messageKey = :messageKey")
    Optional<TranslationEntity> findByLanguageCodeAndMessageKey(@Param("languageCode") String languageCode,
                                                                @Param("messageKey") String messageKey);

    @Query("SELECT t.translatedText FROM TranslationEntity t JOIN t.language l JOIN t.message m WHERE l.code = :languageCode AND m.messageKey = :messageKey")
    Optional<String> findTranslatedTextByLanguageCodeAndMessageKey(@Param("languageCode") String languageCode,
                                                                   @Param("messageKey") String messageKey);

    boolean existsByLanguageIdAndMessageId(Long languageId, Long messageId);
}