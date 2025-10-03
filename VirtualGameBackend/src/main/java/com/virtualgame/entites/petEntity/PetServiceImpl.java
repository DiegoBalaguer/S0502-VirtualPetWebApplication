package com.virtualgame.entites.petEntity;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petEntity.dto.*;
import com.virtualgame.entites.petEntity.mapper.PetCreateDtoMapper;
import com.virtualgame.entites.petEntity.mapper.PetRespAdminDtoMapper;
import com.virtualgame.entites.petEntity.mapper.PetUpdateDtoMapper;
import com.virtualgame.entites.petImage.PetImageRepository;
import com.virtualgame.exception.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl {

    private final PetRepository petRepository;
    private final PetImageRepository petImageRepository;
    private final PetCreateDtoMapper petCreateDtoMapper;
    private final PetRespAdminDtoMapper petRespAdminDtoMapper;
    private final PetUpdateDtoMapper petUpdateDtoMapper;
    private static final String NAME_OBJECT = "pet entity";
    private final AppProperties appProperties;

    @Transactional
    public PetRespAdminDto createPetEntity(PetCreateDto createDto, Long userId) {
        log.debug("Creating new {} with name: {}",NAME_OBJECT, createDto.name());

        PetEntity petEntity = petCreateDtoMapper.toEntity(createDto);
        if (petEntity.getImageUrl() == null || petEntity.getImageUrl().isEmpty()) {
            petEntity.setImageUrl(appProperties.getDefaultPetEntityImageUrl());
        }
        petEntity.setCreatedAt(LocalDateTime.now());
        petEntity.setUpdatedAt(LocalDateTime.now());
        petEntity.setUpdatedBy(userId);

        PetEntity savedEntity = savePetEntity(petEntity);
        log.info("Created {} successfully with ID: {}",NAME_OBJECT, savedEntity.getId());

        return petRespAdminDtoMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public PetRespAdminDto findPetEntityById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);

        PetEntity findEntity = findById(id);

        log.debug("Found {}: {}", NAME_OBJECT, findEntity.getName());
        return petRespAdminDtoMapper.toDto(findEntity);
    }

    @Transactional(readOnly = true)
    public List<PetRespAdminDto> findAllPetEntity() {
        log.debug("Finding all {}", NAME_OBJECT);

        List<PetEntity> petEntity = petRepository.findAll();
        log.info("Found {} {}", petEntity.size(), NAME_OBJECT);

        return petEntity.stream()
                .map(petRespAdminDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PetRespAdminDto updatePetEntity(Long id, PetUpdateDto updateDto, Long userId) {
        log.debug("Updating {} with ID: {}", NAME_OBJECT, id);

        PetEntity petEntity = findById(id);
        petUpdateDtoMapper.forUpdateEntityFromDto(updateDto, petEntity);

        petEntity.setUpdatedAt(LocalDateTime.now());
        petEntity.setUpdatedBy(userId);

        PetEntity updatedPetEntity = savePetEntity(petEntity);
        log.info("Updated successfully {} with ID: {}", NAME_OBJECT, id);

        return petRespAdminDtoMapper.toDto(updatedPetEntity);
    }

    @Transactional
    public void softDeletePetEntity(Long id, Long userId) {
        log.debug("Soft deleting {} with ID: {}", NAME_OBJECT, id);

        PetEntity petEntity = findById(id);

        petEntity.setDeletedAt(LocalDateTime.now());
        petEntity.setDeletedBy(userId);

        savePetEntity(petEntity);
        log.info("Soft deleted successfully {} with ID: {}", NAME_OBJECT, id);
    }

    @Transactional
    public void deletePetEntity(Long id) {
        log.debug("Hard deleting {} with ID: {}", NAME_OBJECT, id);

       findById(id);

        petRepository.deleteById(id);
        log.info("Hard deleted successfully {} with ID: {}", NAME_OBJECT, id);
    }

    @Transactional(readOnly = true)
    public PetEntity findById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);
        return petRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Not found {} with ID: {}", NAME_OBJECT, id);
                    return new NotFoundException("Not found " + NAME_OBJECT + " with ID: " + id);
                });
    }

    @Transactional
    public PetEntity savePetEntity(PetEntity entitySave) {
        log.debug("Saving {}: {}", NAME_OBJECT, entitySave);
        return petRepository.save(entitySave);
    }
}
