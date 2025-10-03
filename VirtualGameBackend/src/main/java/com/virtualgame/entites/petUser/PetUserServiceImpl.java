package com.virtualgame.entites.petUser;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petEntity.PetServiceImpl;
import com.virtualgame.entites.petEntity.dto.PetRespAdminDto;
import com.virtualgame.entites.petUser.dto.*;
import com.virtualgame.entites.petUser.mapper.PetUserCreateDtoMapper;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import com.virtualgame.entites.petUser.tasks.PetUserCreateTaskAdminDto;
import com.virtualgame.exception.exceptions.*;
import com.virtualgame.security.user.auth.dto.AuthSecurityUserDto;
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
public class PetUserServiceImpl {

    private final PetUserRepository petUserRepository;
    private final PetServiceImpl petServiceImpl;
    private final PetUserCreateTaskAdminDto petUserCreateTaskAdminDto;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;
    private final PetUserCreateDtoMapper petUserCreateDtoMapper;
    private final TranslationManagerService translate;
    private final AppProperties appProperties;
    private static final String NAME_OBJECT = "pet user entity";

    @Transactional
    public PetUserRespTaskAdminDto createPetUser(PetUserCreateDto createDto, Long userAuthId) {
        log.debug("Creating new {} with name: {}", NAME_OBJECT, createDto.name());

        PetUser petUser = petUserCreateDtoMapper.toEntity(createDto);
        PetRespAdminDto petEntityDto = petServiceImpl.findPetEntityById(petUser.getPetTypeId());
        if (petUser.getImageUrl() == null || petUser.getImageUrl().isEmpty()) {
            if (!(petEntityDto.imageUrl() == null || petEntityDto.imageUrl().isEmpty())) {
                petUser.setImageUrl(petEntityDto.imageUrl());
            } else {
                petUser.setImageUrl(appProperties.getDefaultPetUserEntityImageUrl());
            }
        }
        petUser.setUserId(userAuthId);
        petUser.setPetHabitatId(appProperties.getDefaultPetHabitatCreatePetUser());
        petUser.setMonths(appProperties.getDefaultPetMonths());
        petUser.setAge(appProperties.getDefaultPetAge());
        petUser.setHappy(appProperties.getDefaultPetHappy());
        petUser.setTired(appProperties.getDefaultPetTired());
        petUser.setHungry(appProperties.getDefaultPetHungry());
        petUser.setHappyReps(appProperties.getDefaultPetHappyReps());
        petUser.setTiredReps(appProperties.getDefaultPetTiredReps());
        petUser.setHungryReps(appProperties.getDefaultPetHungryReps());
        petUser.setCreatedAt(LocalDateTime.now());
        petUser.setCreatedBy(userAuthId);

        PetUser savedEntity = saveUserPet(petUser, userAuthId);
        log.info("Created {} successfully with ID: {}", NAME_OBJECT, savedEntity.getId());
        return petUserCreateTaskAdminDto.getPetUserTaskAdminDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public PetUserRespTaskAdminDto findPetUserById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);

        PetUser findEntity = findById(id);

        log.debug("Found {}: {}", NAME_OBJECT, findEntity.getName());
        return petUserCreateTaskAdminDto.getPetUserTaskAdminDto(findEntity);
    }

    @Transactional(readOnly = true)
    public List<PetUserRespTaskAdminDto> findAllPetsUser() {
        log.debug("Finding all {}", NAME_OBJECT);

        List<PetUser> pets = petUserRepository.findAll();
        log.info("Found {} {}", pets.size(), NAME_OBJECT);

        return pets.stream()
                .map(petUserCreateTaskAdminDto::getPetUserTaskAdminDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PetUserRespTaskAdminDto> findPetsUserByUserId(Long userId) {
        log.debug("Finding all by userId {}, userId: {}", NAME_OBJECT, userId);

        List<PetUser> pets = petUserRepository.findByUserId(userId);
        log.info("Found {} {}", pets.size(), NAME_OBJECT);

        return pets.stream()
                .map(petUserCreateTaskAdminDto::getPetUserTaskAdminDto)
                .toList();
    }

    @Transactional
    public PetUserRespTaskAdminDto updatePetUser(Long id, PetUserRespAdminDto petUserRespAdminDto, Long userAuthId) {
        log.debug("Updating {} with ID: {}", NAME_OBJECT, id);

        PetUser userPet = findById(id);
        petUserRespAdminDtoMapper.updateEntityFromDto(petUserRespAdminDto, userPet);

        PetUser updatedPet = saveUserPet(userPet, userAuthId);
        log.info("Updated successfully {} with ID: {}", NAME_OBJECT, id);

        return petUserCreateTaskAdminDto.getPetUserTaskAdminDto(updatedPet);
    }

    @Transactional
    public void softDeletePetUserByUserId(AuthSecurityUserDto authSecurityUserDto, Long deleteUserId) {
        log.debug("Soft deleting {} with ID: {}", NAME_OBJECT, deleteUserId);

        PetUser userPet = findById(deleteUserId);

        userPet.setDeletedAt(LocalDateTime.now());
        userPet.setDeletedBy(authSecurityUserDto.userId());

        saveUserPet(userPet, authSecurityUserDto.userId());
        log.info("Soft deleted successfully {} with ID: {}", NAME_OBJECT, deleteUserId);
    }

    @Transactional
    public void hardDeletePetUserByUserId(Long userId) {
        log.debug("Hard deleting {} with proprietary user ID: {}", NAME_OBJECT, userId);

        petUserRepository.deleteByUserId(userId);
        log.info("Hard deleted successfully {} with proprietary user ID: {}", NAME_OBJECT, userId);
    }

    @Transactional
    public void hardDeletePetUserById(Long petUserId) {
        log.debug("Hard deleting {} with ID: {}", NAME_OBJECT, petUserId);

        petUserRepository.deleteById(petUserId);
        log.info("Hard deleted successfully {} with ID: {}", NAME_OBJECT, petUserId);
    }

    @Transactional(readOnly = true)
    public List<PetUserRespTaskAdminDto> findPetsUserByType(Long petTypeId) {
        log.debug("Finding {} by type ID: {}", NAME_OBJECT, petTypeId);

        List<PetUser> pets = petUserRepository.findByPetTypeId(petTypeId);
        log.info("Found {} {} with type ID: {}", pets.size(), NAME_OBJECT, petTypeId);

        return pets.stream()
                .map(petUserCreateTaskAdminDto::getPetUserTaskAdminDto)
                .toList();
    }

    @Transactional(readOnly = true)
    protected PetUser findById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);
        return petUserRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Not found {} with ID: {}", NAME_OBJECT, id);
                    return new NotFoundException("Not found " + NAME_OBJECT + " with ID: " + id);
                });
    }

    @Transactional
    protected PetUser saveUserPet(PetUser entitySave, Long userAuthId) {
        log.debug("Saving {}: {}", NAME_OBJECT, entitySave);

        entitySave.setUpdatedAt(LocalDateTime.now());
        entitySave.setUpdatedBy(userAuthId);

        return petUserRepository.save(entitySave);
    }
}
