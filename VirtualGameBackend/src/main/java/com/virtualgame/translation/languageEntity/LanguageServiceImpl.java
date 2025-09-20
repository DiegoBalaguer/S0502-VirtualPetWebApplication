package com.virtualgame.translation.languageEntity;

import com.virtualgame.exception.exceptions.NotFoundException;
import com.virtualgame.translation.TranslationCacheService;
import com.virtualgame.translation.TranslationManagerService;
import com.virtualgame.translation.languageEntity.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LanguageServiceImpl {

    private final LanguageRepository languageRepository;
    private final TranslationCacheService translationCacheService;
    private final LanguageCreateDtoMapper  languageCreateDtoMapper;
    private final LanguageDtoMapper languageDtoMapper;
    private final TranslationManagerService translate;
    private static final String NAME_OBJECT = "language service";
    private final LanguageUpdateDtoMapper languageUpdateDtoMapper;

    @Transactional
    public LanguageDto createLanguage(LanguageCreateDto createDto, Long userId) {
        log.debug(translate
                .getFormatSys("Creating new {0} with name: {1}", NAME_OBJECT, createDto.name()));

        if (existsByCode(createDto.code())) {
            String message = translate.
                    getFormatSys("{0} with name {1} already exists", NAME_OBJECT, createDto.name());
            log.debug(message);
            throw new RuntimeException(message);
        }

        LanguageEntity createEntity = languageCreateDtoMapper.toEntity(createDto);
        createEntity.setCreatedBy(userId);

        LanguageEntity savedEntity = saveLanguage(createEntity);

        log.debug(translate
                .getFormatSys("Created {0} successfully with ID: {1} and name: {2}", NAME_OBJECT, savedEntity.getId(), savedEntity.getName()));

        return languageDtoMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public Boolean existsByCode(String code) {
        log.debug(translate
                .getFormatSys("Find exists {0} with ID: {1}", NAME_OBJECT, code));
        return languageRepository.existsByCode(code);
    }

    @Transactional(readOnly = true)
    public LanguageEntity findById(Long id) {
        log.debug(translate
                .getFormatSys("Finding {0} by ID: {1}", NAME_OBJECT, id));
        return languageRepository.findById(id)
                .orElseThrow(() -> {
                    String message = translate.getFormatSys("Not found {0} with ID: {1}", NAME_OBJECT, id);
                    log.warn(message);
                    return new NotFoundException(message);
                });
    }

    @Transactional(readOnly = true)
    public List<LanguageDto> findAllLanguage() {
        log.debug(translate
                .getFormatSys("Finding all {0}", NAME_OBJECT));

        List<LanguageEntity> findEntities = languageRepository.findAll();

        log.debug(translate
                .getFormatSys("Found {0} units in {1}", findEntities.size(), NAME_OBJECT));

        return findEntities.stream()
                .map(languageDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LanguageDto findLanguageById(Long id) {
        log.debug(translate
                .getFormatSys("Finding {0} by ID: {1}", NAME_OBJECT, id));

        LanguageEntity  findEntity = findById(id);

        log.debug(translate
                .getFormatSys("Found {0}: {1}", NAME_OBJECT, findEntity.getName()));

        return languageDtoMapper.toDto((findEntity));
    }

    @Transactional(readOnly = true)
    public Optional<LanguageDto> findLanguageByCode(String code) {
        log.info("Finding language by code: {}", code);
        return languageRepository.findByCode(code)
                .map(languageDtoMapper::toDto);
    }

    @Transactional
    public LanguageDto updateLanguage(Long id, LanguageUpdateDto updateDto, Long userId) {
        log.debug(translate
                .getFormatSys("Updating {0} with ID: {1}", NAME_OBJECT, id));

        LanguageEntity existing = findById(id);
        languageUpdateDtoMapper.forUpdateEntityFromDto(updateDto, existing);
        existing.setUpdatedBy(userId);

        LanguageEntity updated = saveLanguage(existing);

        log.debug(translate
                .getFormatSys("Updated successfully {0} with ID: {1}", NAME_OBJECT, id));
        return languageDtoMapper.toDto(updated);
    }


    @Transactional
    public void softDeleteLanguage(Long id, Long userId) {
        log.debug(translate
                .getFormatSys("Soft deleting {0} with ID: {1}", NAME_OBJECT, id));

        LanguageEntity existing = findById(id);

        existing.setDeletedAt(LocalDateTime.now());
        existing.setDeletedBy(userId);

        saveLanguage(existing);
        log.debug(translate
                .getFormatSys("Soft deleted successfully {0} with ID: {1}", NAME_OBJECT, id));
    }

    @Transactional
    public void deleteLanguage(Long id) {
        log.debug(translate
                .getFormatSys("Hard deleting {0} with ID: {1}", NAME_OBJECT, id));

        translationCacheService.clearCache();

        log.debug(translate
                .getFormatSys("Hard deleted successfully {0} with ID: {1}", NAME_OBJECT, id));
    }

    @Transactional
    public LanguageEntity saveLanguage(LanguageEntity entitySave) {
        log.debug(translate
                .getFormatSys("Saving {0}: {1}", NAME_OBJECT, entitySave));

        if (entitySave.getCode() != null) entitySave.setCode(entitySave.getCode().toLowerCase());
        LanguageEntity savedEntity = languageRepository.save(entitySave);

        translationCacheService.clearCache();

        log.debug(translate
                .getFormatSys("Saved {0} successfully with ID: {1} and name: {2}", NAME_OBJECT, savedEntity.getId(), savedEntity.getName()));
        return savedEntity;
    }

}
