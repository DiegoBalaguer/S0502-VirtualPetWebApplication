package com.virtualgame.entites.petUser;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petUser.dto.*;
import com.virtualgame.entites.petUser.mapper.PetUserCreateDtoMapper;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
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
public class PetUserServiceImpl {

    private final PetUserRepository petUserRepository;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;
    private final PetUserCreateDtoMapper petUserCreateDtoMapper;
    private final AppProperties appProperties;
    private static final String NAME_OBJECT = "pet user entity";

    @Transactional
    public PetUserRespAdminDto createPetUser(PetUserCreateDto createDto, Long userId) {
        log.debug("Creating new {} with name: {}", NAME_OBJECT, createDto.name());

        PetUser pet = petUserCreateDtoMapper.toEntity(createDto);
        if (pet.getImageUrl() == null || pet.getImageUrl().isEmpty()) {
            pet.setImageUrl(appProperties.getDefaultPetUserEntityImageUrl());
        }
        pet.setHappy(appProperties.getDefaultPetHappy());
        pet.setTired(appProperties.getDefaultPetTired());
        pet.setHungry(appProperties.getDefaultPetHungry());
        pet.setCreatedAt(LocalDateTime.now());
        pet.setUpdatedAt(LocalDateTime.now());
        pet.setUpdatedBy(userId);

        PetUser savedEntity = saveUserPet(pet);
        log.info("Created {} successfully with ID: {}", NAME_OBJECT, savedEntity.getId());

        return petUserRespAdminDtoMapper.toDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public PetUserRespAdminDto findPetUserById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);

        PetUser findEntity = findById(id);

        log.debug("Found {}: {}", NAME_OBJECT, findEntity.getName());
        return petUserRespAdminDtoMapper.toDto(findEntity);
    }

    @Transactional(readOnly = true)
    public List<PetUserRespAdminDto> findAllPetsUser() {
        log.debug("Finding all {}", NAME_OBJECT);

        List<PetUser> pets = petUserRepository.findAll();
        log.info("Found {} {}", pets.size(), NAME_OBJECT);

        return pets.stream()
                .map(petUserRespAdminDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public PetUserRespAdminDto updatePetUser(Long id, PetUserRespAdminDto petUserRespAdminDto, Long userId) {
        log.debug("Updating {} with ID: {}", NAME_OBJECT, id);

        PetUser userPet = findById(id);
        petUserRespAdminDtoMapper.updateEntityFromDto(petUserRespAdminDto, userPet);

        userPet.setUpdatedAt(LocalDateTime.now());
        userPet.setUpdatedBy(userId);

        PetUser updatedPet = saveUserPet(userPet);
        log.info("Updated successfully {} with ID: {}", NAME_OBJECT, id);

        return petUserRespAdminDtoMapper.toDto(updatedPet);
    }

    @Transactional
    public void softDeletePetUser(Long id, Long userId) {
        log.debug("Soft deleting {} with ID: {}", NAME_OBJECT, id);

        PetUser userPet = findById(id);

        userPet.setDeletedAt(LocalDateTime.now());
        userPet.setDeletedBy(userId);

        saveUserPet(userPet);
        log.info("Soft deleted successfully {} with ID: {}", NAME_OBJECT, id);
    }

    @Transactional
    public void softDeletePetUserByUserId(Long userId, Long userIdAuth) {
        log.debug("Soft deleting {} with with proprietary user ID: {}", NAME_OBJECT, userId);

        List<PetUser> userPets = petUserRepository.findByUserId(userId);

        userPets.forEach(pet -> {
            pet.setDeletedAt(LocalDateTime.now());
            pet.setDeletedBy(userIdAuth);
        });

        petUserRepository.saveAll(userPets);
        log.info("Soft deleted successfully {} with proprietary user ID: {}", NAME_OBJECT, userId);
    }

    @Transactional
    public void hardDeletePetUser(Long id) {
        log.debug("Hard deleting {} with ID: {}", NAME_OBJECT, id);

        findById(id);

        petUserRepository.deleteById(id);
        log.info("Hard deleted successfully {} with ID: {}", NAME_OBJECT, id);
    }

    @Transactional
    public void hardDeletePetUserByUserId(Long userId) {
        log.debug("Hard deleting {} with proprietary user ID: {}", NAME_OBJECT, userId);

        petUserRepository.deleteByUserId(userId);
        log.info("Hard deleted successfully {} with proprietary user ID: {}", NAME_OBJECT, userId);
    }


    @Transactional(readOnly = true)
    public List<PetUserRespAdminDto> findPetsUserByType(Long petTypeId) {
        log.debug("Finding {} by type ID: {}", NAME_OBJECT, petTypeId);

        List<PetUser> pets = petUserRepository.findByPetTypeId(petTypeId);
        log.info("Found {} {} with type ID: {}", pets.size(), NAME_OBJECT, petTypeId);

        return pets.stream()
                .map(petUserRespAdminDtoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PetUser findById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);
        return petUserRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Not found {} with ID: {}", NAME_OBJECT, id);
                    return new NotFoundException("Not found " + NAME_OBJECT + " with ID: " + id);
                });
    }

    @Transactional
    public PetUser saveUserPet(PetUser entitySave) {
        log.debug("Saving {}: {}", NAME_OBJECT, entitySave);
        return petUserRepository.save(entitySave);
    }
}
