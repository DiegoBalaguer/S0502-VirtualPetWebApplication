package com.virtualgame.config.configApp;

import com.virtualgame.config.configApp.dto.*;
import com.virtualgame.config.configApp.mapper.AppConfigurationCreateDtoMapper;
import com.virtualgame.config.configApp.mapper.AppConfigurationRespAdminDtoMapper;
import com.virtualgame.config.configApp.mapper.AppConfigurationUpdateDtoMapper;
import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.exception.exceptions.NotFoundException;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppConfigurationService {

    private final AppConfigurationRepository appConfigurationRepository;
    private final AppConfigurationCreateDtoMapper appConfigurationCreateDtoMapper;
    private final AppConfigurationRespAdminDtoMapper appConfigurationRespAdminDtoMapper;
    private final AppConfigurationUpdateDtoMapper appConfigurationUpdateDtoMapper;
    private final TranslationManagerService translate;
    private final AppProperties appPrp;
    private static final String NAME_OBJECT = "app parameters";

    public Map<String, AppConfiguration> findAllAppConfigurationAsMap() {
        return appConfigurationRepository.findAll().stream()
                .collect(Collectors.toMap(
                        config -> config.getKeyName().toLowerCase(),
                        config -> config
                ));
    }


    @Transactional
    public AppConfigurationRespAdminDto create(AppConfigurationCreateDto createDto, Long userId) {
        log.debug(translate
                .getFormatSys("Creating new {0} with name: {1}", NAME_OBJECT, createDto.keyName()));

        if (existsByName(createDto.keyName())) {
            String message = translate.
                    getFormatSys("{0} with name {1} already exists", NAME_OBJECT, createDto.keyName());
            log.debug(message);
            throw new RuntimeException(message);
        }

        AppConfiguration createEntity = appConfigurationCreateDtoMapper.toEntity(createDto);

        AppConfiguration savedEntity = saveAppConfiguration(createEntity, userId);

        log.debug(translate
                .getFormatSys("Created {0} successfully with ID: {1} and name: {2}", NAME_OBJECT, savedEntity.getId(), savedEntity.getKeyName()));

        return appConfigurationRespAdminDtoMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    protected Boolean existsByName(String name) {
        log.debug(translate
                .getFormatSys("Find exists {0} with name: {1}", NAME_OBJECT, name));
        return appConfigurationRepository.existsByKeyName(name);
    }

    @Cacheable(value = "appSettings", key = "#id")
    @Transactional(readOnly = true)
    public AppConfiguration findById(Long id) {
        log.debug(translate
                .getFormatSys("Finding {0} by ID: {1}", NAME_OBJECT, id));
        return appConfigurationRepository.findById(id)
                .orElseThrow(() -> {
                    String message = translate.getFormatSys("Not found {0} with ID: {1}", NAME_OBJECT, id);
                    log.warn(message);
                    return new NotFoundException(message);
                });
    }

    @Cacheable(value = "appSettings", key = "#id")
    @Transactional(readOnly = true)
    public AppConfigurationRespAdminDto findAppConfigurationById(Long id) {
        log.debug(translate
                .getFormatSys("Finding {0} by ID: {1}", NAME_OBJECT, id));

        AppConfiguration findEntity = findById(id);

        log.debug(translate
                .getFormatSys("Found {0}: {1}", NAME_OBJECT, findEntity.getKeyName()));
        return appConfigurationRespAdminDtoMapper.toDto(findEntity);
    }

    @Cacheable(value = "appSettings", key = "#id")
    @Transactional(readOnly = true)
    public List<AppConfigurationRespAdminDto> findAllAppConfiguration() {
        log.debug(translate
                .getFormatSys("Finding all {0}", NAME_OBJECT));

        List<AppConfiguration> findEntities = appConfigurationRepository.findAll();

        log.debug(translate
                .getFormatSys("Found {0} units in {1}", findEntities.size(), NAME_OBJECT));

        return findEntities.stream()
                .map(appConfigurationRespAdminDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public AppConfigurationRespAdminDto updateAppConfiguration(Long id, AppConfigurationUpdateDto updateDto, Long userId) {
        log.debug(translate
                .getFormatSys("Updating {0} with ID: {1}", NAME_OBJECT, id));

        AppConfiguration existing = findById(id);
        appConfigurationUpdateDtoMapper.forUpdateEntityFromDto(updateDto, existing);

        AppConfiguration updated = saveAppConfiguration(existing, userId);

        log.debug(translate
                .getFormatSys("Updated successfully {0} with ID: {1}", NAME_OBJECT, id));
        return appConfigurationRespAdminDtoMapper.toDto(updated);
    }

    @Transactional
    public void deleteAppConfiguration(Long id) {
        log.debug(translate
                .getFormatSys("Hard deleting {0} with ID: {1}", NAME_OBJECT, id));

        appConfigurationRepository.delete(findById(id));

        log.debug(translate
                .getFormatSys("Hard deleted successfully {0} with ID: {1}", NAME_OBJECT, id));
    }

    @Transactional
    protected AppConfiguration saveAppConfiguration(AppConfiguration entitySave, Long userAuthId) {
        log.debug(translate
                .getFormatSys("Saving {0}: {1}", NAME_OBJECT, entitySave));

        AppConfiguration savedEntity = appConfigurationRepository.save(entitySave);

        log.debug(translate
                .getFormatSys("Saved {0} successfully with ID: {1} and name: {2}", NAME_OBJECT, savedEntity.getId(), savedEntity.getKeyName()));
        return savedEntity;
    }
}
