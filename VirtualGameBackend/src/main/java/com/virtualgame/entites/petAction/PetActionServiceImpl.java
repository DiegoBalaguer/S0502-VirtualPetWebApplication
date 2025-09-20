package com.virtualgame.entites.petAction;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petAction.dto.*;
import com.virtualgame.entites.petAction.mapper.PetActionCreateDtoMapper;
import com.virtualgame.entites.petAction.mapper.PetActionRespAdminDtoMapper;
import com.virtualgame.entites.petAction.mapper.PetActionUpdateAdminDtoMapper;
import com.virtualgame.exception.exceptions.NotFoundException;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetActionServiceImpl {

    private final PetActionRepository petActionRepository;
    private final PetActionCreateDtoMapper petActionCreateDtoMapper;
    private final PetActionRespAdminDtoMapper petActionRespAdminDtoMapper;
    private final PetActionUpdateAdminDtoMapper petActionUpdateAdminDtoMapper;
    private final TranslationManagerService translate;
    private final AppProperties appPrp;
    private static final String NAME_OBJECT = "pet action";

    @Transactional
    public PetActionRespAdminDto createPetAction(PetActionCreateDto createDto, Long userId) {
        log.debug(translate
                .getFormatSys("Creating new {0} with name: {1}", NAME_OBJECT, createDto.name()));

        if (existsByName(createDto.name())) {
            String message = translate.
                    getFormatSys("{0} with name {1} already exists", NAME_OBJECT, createDto.name());
            log.debug(message);
            throw new RuntimeException(message);
        }

        PetAction createEntity = petActionCreateDtoMapper.toEntity(createDto);
        if (createEntity.getImageUrl() == null || createEntity.getImageUrl().isEmpty()) {
            createEntity.setImageUrl(appPrp.getDefaultPetActionImageUrl());
        }
        if (createEntity.getHappy() == null) createEntity.setHappy(appPrp.getDefaultPetHappy());
        if (createEntity.getTired() == null) createEntity.setTired(appPrp.getDefaultPetTired());
        if (createEntity.getHungry() == null) createEntity.setHungry(appPrp.getDefaultPetHungry());
        if (createEntity.getHappyMax() == null) createEntity.setHappyMax(appPrp.getDefaultPetMonths());
        if (createEntity.getAge() == null) createEntity.setAge(appPrp.getDefaultPetAge());
        createEntity.setCreatedBy(userId);

        PetAction savedEntity = savePetAction(createEntity);

        log.debug(translate
                .getFormatSys("Created {0} successfully with ID: {1} and name: {2}", NAME_OBJECT, savedEntity.getId(), savedEntity.getName()));

        return petActionRespAdminDtoMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    protected Boolean existsByName(String name) {
        log.debug(translate
                .getFormatSys("Find exists {0} with name: {1}", NAME_OBJECT, name));
        return petActionRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    protected PetAction findById(Long id) {
        log.debug(translate
                .getFormatSys("Finding {0} by ID: {1}", NAME_OBJECT, id));
        return petActionRepository.findById(id)
                .orElseThrow(() -> {
                    String message = translate.getFormatSys("Not found {0} with ID: {1}", NAME_OBJECT, id);
                    log.warn(message);
                    return new NotFoundException(message);
                });
    }

    @Transactional(readOnly = true)
    public PetActionRespAdminDto findPetActionById(Long id) {
        log.debug(translate
                .getFormatSys("Finding {0} by ID: {1}", NAME_OBJECT, id));

        PetAction findEntity = findById(id);

        log.debug(translate
                .getFormatSys("Found {0}: {1}", NAME_OBJECT, findEntity.getName()));
        return petActionRespAdminDtoMapper.toDto(findEntity);
    }

    @Transactional(readOnly = true)
    public List<PetActionRespAdminDto> findAllPetAction() {
        log.debug(translate
                .getFormatSys("Finding all {0}", NAME_OBJECT));

        List<PetAction> findEntities = petActionRepository.findAll();

        log.debug(translate
                .getFormatSys("Found {0} units in {1}", findEntities.size(), NAME_OBJECT));

        return findEntities.stream()
                .map(petActionRespAdminDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PetActionRespAdminDto updatePetAction(Long id, PetActionUpdateDto updateDto, Long userId) {
        log.debug(translate
                .getFormatSys("Updating {0} with ID: {1}", NAME_OBJECT, id));

        PetAction existing = findById(id);
        petActionUpdateAdminDtoMapper.forUpdateEntityFromDto(updateDto, existing);
        existing.setUpdatedBy(userId);

        PetAction updated = savePetAction(existing);

        log.debug(translate
                .getFormatSys("Updated successfully {0} with ID: {1}", NAME_OBJECT, id));
        return petActionRespAdminDtoMapper.toDto(updated);
    }

    @Transactional
    public PetActionRespAdminDto deleteSoftPetAction(Long id, Long userId) {
        log.debug(translate
                .getFormatSys("Soft deleting {0} with ID: {1}", NAME_OBJECT, id));

        PetAction existing = findById(id);

        existing.setDeletedAt(LocalDateTime.now());
        existing.setDeletedBy(userId);

        PetAction updated = savePetAction(existing);

        log.debug(translate
                .getFormatSys("Soft deleted successfully {0} with ID: {1}", NAME_OBJECT, id));
        return petActionRespAdminDtoMapper.toDto(updated);
    }

    @Transactional
    public void deletePetAction(Long id) {
        log.debug(translate
                .getFormatSys("Hard deleting {0} with ID: {1}", NAME_OBJECT, id));

        petActionRepository.delete(findById(id));

        log.debug(translate
                .getFormatSys("Hard deleted successfully {0} with ID: {1}", NAME_OBJECT, id));
    }

    @Transactional
    protected PetAction savePetAction(PetAction entitySave) {
        log.debug(translate
                .getFormatSys("Saving {0}: {1}", NAME_OBJECT, entitySave));

        if (entitySave.getName() != null) entitySave.setName(entitySave.getName().toUpperCase());
        PetAction savedEntity = petActionRepository.save(entitySave);

        log.debug(translate
                .getFormatSys("Saved {0} successfully with ID: {1} and name: {2}", NAME_OBJECT, savedEntity.getId(), savedEntity.getName()));
        return savedEntity;
    }
}
