package com.virtualgame.entites.petHabitat;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petHabitat.dto.*;
import com.virtualgame.entites.petHabitat.mapper.PetHabitatCreateDtoMapper;
import com.virtualgame.entites.petHabitat.mapper.PetHabitatRespAdminDtoMapper;
import com.virtualgame.entites.petHabitat.mapper.PetHabitatUpdateDtoMapper;
import com.virtualgame.exception.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetHabitatServiceImpl {

    private final PetHabitatRepository petHabitatRepository;
    private final PetHabitatCreateDtoMapper petHabitatCreateDtoMapper;
    private final PetHabitatRespAdminDtoMapper petHabitatRespAdminDtoMapper;
    private final PetHabitatUpdateDtoMapper petHabitatUpdateDtoMapper;
/*
    @Lazy
    @Autowired
    private PetHabitatServiceImpl self;
*/
    public static final String ALL_PET_ENTITIES_KEY = "'allPetsList'"; // se han de dejar las comillas simples!!

    private static final String NAME_OBJECT = "pet habitat";
    private final AppProperties appProperties;

    @Transactional
    public PetHabitatRespAdminDto createPetHabitat(PetHabitatCreateDto createDto, Long userId) {
        log.debug("Creating new {} with name: {}", NAME_OBJECT, createDto.name());

        PetHabitat petHabitat = petHabitatCreateDtoMapper.toEntity(createDto);
        if (petHabitat.getImageUrl() == null || petHabitat.getImageUrl().isEmpty()) {
            petHabitat.setImageUrl(appProperties.getDefaultPetHabitatImageUrl());
        }
        if (petHabitat.getHappy() == null) petHabitat.setHappy(appProperties.getDefaultPetHappy());
        if (petHabitat.getTired() == null) petHabitat.setTired(appProperties.getDefaultPetTired());
        if (petHabitat.getHungry() == null) petHabitat.setHungry(appProperties.getDefaultPetHungry());
        if (petHabitat.getMonths() == null) petHabitat.setMonths(appProperties.getDefaultPetMonths());
        if (petHabitat.getAge() == null) petHabitat.setAge(appProperties.getDefaultPetAge());

        petHabitat.setCreatedAt(LocalDateTime.now());
        petHabitat.setUpdatedAt(LocalDateTime.now());
        petHabitat.setUpdatedBy(userId);

        PetHabitat savedEntity = savePetHabitat(petHabitat);
        log.info("Created {} successfully with ID: {}", NAME_OBJECT, savedEntity.getId());

        return petHabitatRespAdminDtoMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "petHabitat", key = "#id")
    public PetHabitatRespAdminDto findPetHabitatById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);

        //PetHabitat findEntity = self.findById(id);
        Optional<PetHabitat> findEntity = petHabitatRepository.findById(id);

        log.debug("Found {}: {}", NAME_OBJECT, findEntity.get().getName());
        return petHabitatRespAdminDtoMapper.toDto(findEntity.get());
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "petHabitatsAll", key = ALL_PET_ENTITIES_KEY)
    public List<PetHabitatRespAdminDto> findAllPetHabitat() {
        log.debug("Finding all {}", NAME_OBJECT);

        List<PetHabitat> petHabitatList = petHabitatRepository.findAll();
        log.info("Found {} {}", petHabitatList.size(), NAME_OBJECT);

        return petHabitatList.stream()
                .map(petHabitatRespAdminDtoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PetHabitatRespAdminDto> findAllPetHabitatByParentId(Long petHabitatId) {
        log.debug("Finding all by parentId {} by ID: {}", NAME_OBJECT, petHabitatId);

        Long parentId = findParentId(petHabitatId);
        List<PetHabitatRespAdminDto> petHabitatList = findAllPetHabitatByParentIdList(parentId);

        log.info("Found {} {}", petHabitatList.size(), NAME_OBJECT);

        return petHabitatList;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "petHabitatsByParentIdAll", key = "#parentId")
    public List<PetHabitatRespAdminDto> findAllPetHabitatByParentIdList(Long parentId) {
        log.debug("Finding all by parentId List {} by parentId: {}", NAME_OBJECT, parentId);

        List<PetHabitat> petHabitatList = petHabitatRepository.findAllByParentId(parentId);

        log.info("Found {} {}", petHabitatList.size(), NAME_OBJECT);

        return petHabitatList.stream()
                .map(petHabitatRespAdminDtoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Long findParentId(Long petHabitatId) {
        log.debug("Finding parentId {} by ID: {}", NAME_OBJECT, petHabitatId);

        PetHabitat petHabitat = findById(petHabitatId);

        return petHabitat.getParentId();
    }

    @Transactional
    public PetHabitatRespAdminDto updatePetHabitat(Long id, PetHabitatUpdateDto updateDto, Long userId) {
        log.debug("Updating {} with ID: {}", NAME_OBJECT, id);

        PetHabitat petHabitat = findById(id);
        petHabitatUpdateDtoMapper.forUpdateEntityFromDto(updateDto, petHabitat);

        petHabitat.setUpdatedAt(LocalDateTime.now());
        petHabitat.setUpdatedBy(userId);

        PetHabitat updatedPetHabitat = savePetHabitat(petHabitat);
        log.info("Updated successfully {} with ID: {}", NAME_OBJECT, id);

        return petHabitatRespAdminDtoMapper.toDto(updatedPetHabitat);
    }

    @Transactional
    public void softDeletePetHabitat(Long id, Long userId) {
        log.debug("Soft deleting {} with ID: {}", NAME_OBJECT, id);

        PetHabitat petHabitat = findById(id);

        petHabitat.setDeletedAt(LocalDateTime.now());
        petHabitat.setDeletedBy(userId);

        savePetHabitat(petHabitat);
        log.info("Soft deleted successfully {} with ID: {}", NAME_OBJECT, id);
    }

    @Transactional
    @CacheEvict(value = "petHabitat", key = "#id")
    public void deletePetHabitat(Long id) {
        log.debug("Hard deleting {} with ID: {}", NAME_OBJECT, id);

        findById(id);

        petHabitatRepository.deleteById(id);
        log.info("Hard deleted successfully {} with ID: {}", NAME_OBJECT, id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "petHabitat", key = "#id")
    public PetHabitat findById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);
        return petHabitatRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Not found {} with ID: {}", NAME_OBJECT, id);
                    return new NotFoundException("Not found " + NAME_OBJECT + " with ID: " + id);
                });
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "petHabitat", key = "#entitySave.id", beforeInvocation = true),
                    @CacheEvict(value = "petHabitatsAll", allEntries = true, beforeInvocation = true),
                    //@CacheEvict(value = "petHabitatsByParent", key = "#entitySave.parentId", beforeInvocation = true), // Limpia la lista del padre
                    @CacheEvict(value = "petHabitatsByParentIdAll", allEntries = true, beforeInvocation = true)
            })
    @Transactional
    @CachePut(value = "petHabitat", key = "#entitySave.id")
    public PetHabitat savePetHabitat(PetHabitat entitySave) {
        log.debug("Saving {}: {}", NAME_OBJECT, entitySave);
        if (entitySave.getName() != null) entitySave.setName(entitySave.getName().toUpperCase());
        return petHabitatRepository.save(entitySave);
    }

    public boolean isInDomedCity(Long habitatId) {
        Long domedCityId = appProperties.getDefaultPetHabitatDomedCityId();
        PetHabitat petHabitat = findById(habitatId);
        return (petHabitat.getId().equals(domedCityId) || petHabitat.getParentId().equals(domedCityId));
    }

    public boolean isInSanctuary(Long habitatId) {
        Long sanctuaryId = appProperties.getDefaultPetHabitatSanctuaryId();
        PetHabitat petHabitat = findById(habitatId);
        return (petHabitat.getId().equals(sanctuaryId) || petHabitat.getParentId().equals(sanctuaryId));
    }
}
