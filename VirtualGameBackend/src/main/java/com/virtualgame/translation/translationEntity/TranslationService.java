package com.virtualgame.translation.translationEntity;

import com.virtualgame.translation.TranslationCacheService;
import com.virtualgame.translation.languageEntity.LanguageServiceImpl;
import com.virtualgame.translation.messageEntity.MessageService;
import com.virtualgame.translation.translationEntity.dto.TranslationDto;
import com.virtualgame.translation.translationEntity.dto.TranslationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final LanguageServiceImpl languageServiceImpl;
    private final MessageService messageService;
    private final TranslationCacheService translationCacheService;

    public List<TranslationDto> findAll() {
        log.info("Finding all translations");
        return translationRepository.findAll().stream()
                .map(TranslationMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TranslationDto> findById(Long id) {
        log.info("Finding translation by id: {}", id);
        return translationRepository.findById(id)
                .map(TranslationMapper.INSTANCE::toDTO);
    }

    public Optional<TranslationDto> findByLanguageCodeAndMessageKey(String languageCode, String messageKey) {
        log.info("Finding translation for language: {} and message: {}", languageCode, messageKey);
        return translationRepository.findByLanguageCodeAndMessageKey(languageCode, messageKey)
                .map(TranslationMapper.INSTANCE::toDTO);
    }

    @Transactional
    public TranslationDto create(TranslationDto translationDTO) {
        log.info("Creating new translation for languageId: {} and messageId: {}",
                translationDTO.getLanguageId(), translationDTO.getMessageId());

        if (translationRepository.existsByLanguageIdAndMessageId(
                translationDTO.getLanguageId(), translationDTO.getMessageId())) {
            throw new RuntimeException("Translation already exists for this language and message");
        }

        TranslationEntity entity = TranslationMapper.INSTANCE.toEntity(translationDTO);
        TranslationEntity saved = translationRepository.save(entity);

        translationCacheService.reloadCache();
        log.info("Translation created successfully with id: {}", saved.getId());

        return TranslationMapper.INSTANCE.toDTO(saved);
    }

    @Transactional
    public TranslationDto update(Long id, TranslationDto translationDTO) {
        log.info("Updating translation with id: {}", id);
        TranslationEntity existing = translationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Translation not found with id: " + id));

        existing.setTranslatedText(translationDTO.getTranslatedText());

        TranslationEntity updated = translationRepository.save(existing);

        translationCacheService.clearCache();
        log.info("Translation updated successfully with id: {}", updated.getId());

        return TranslationMapper.INSTANCE.toDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting translation with id: {}", id);
        TranslationEntity entity = translationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Translation not found with id: " + id));

        translationRepository.delete(entity);
        translationCacheService.clearCache();
        log.info("Translation deleted successfully with id: {}", id);
    }
}