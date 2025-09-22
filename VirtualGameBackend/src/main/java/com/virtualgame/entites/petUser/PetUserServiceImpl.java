package com.virtualgame.entites.petUser;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petAction.PetActionServiceImpl;
import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import com.virtualgame.entites.petUser.dto.*;
import com.virtualgame.entites.petUser.mapper.PetUserCreateDtoMapper;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import com.virtualgame.exception.exceptions.NotFoundException;
import com.virtualgame.logicRules.RulesDoAction;
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
    private final PetActionServiceImpl petActionServiceImpl;
    private final RulesDoAction  rulesDoAction;
    private final AppProperties appProperties;
    private static final String NAME_OBJECT = "pet user entity";

    @Transactional
    public PetUserRespAdminDto createPetUser(PetUserCreateDto createDto, Long userAuthId) {
        log.debug("Creating new {} with name: {}", NAME_OBJECT, createDto.name());

        PetUser pet = petUserCreateDtoMapper.toEntity(createDto);
        if (pet.getImageUrl() == null || pet.getImageUrl().isEmpty()) {
            pet.setImageUrl(appProperties.getDefaultPetUserEntityImageUrl());
        }
        pet.setMonths(appProperties.getDefaultPetMonths());
        pet.setAge(appProperties.getDefaultPetAge());
        pet.setHappy(appProperties.getDefaultPetHappy());
        pet.setTired(appProperties.getDefaultPetTired());
        pet.setHungry(appProperties.getDefaultPetHungry());
        pet.setHappyReps(appProperties.getDefaultPetHappyReps());
        pet.setTiredReps(appProperties.getDefaultPetTiredReps());
        pet.setHungryReps(appProperties.getDefaultPetHungryReps());
        pet.setCreatedAt(LocalDateTime.now());

        PetUser savedEntity = saveUserPet(pet,  userAuthId);
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
    public PetUserRespAdminDto updatePetUser(Long id, PetUserRespAdminDto petUserRespAdminDto, Long userAuthId) {
        log.debug("Updating {} with ID: {}", NAME_OBJECT, id);

        PetUser userPet = findById(id);
        petUserRespAdminDtoMapper.updateEntityFromDto(petUserRespAdminDto, userPet);

        PetUser updatedPet = saveUserPet(userPet,  userAuthId);
        log.info("Updated successfully {} with ID: {}", NAME_OBJECT, id);

        return petUserRespAdminDtoMapper.toDto(updatedPet);
    }

    @Transactional
    public void softDeletePetUserByUserId(Long id, Long userAuthId) {
        log.debug("Soft deleting {} with ID: {}", NAME_OBJECT, id);

        PetUser userPet = findById(id);

        userPet.setDeletedAt(LocalDateTime.now());
        userPet.setDeletedBy(userAuthId);

        saveUserPet(userPet, userAuthId);
        log.info("Soft deleted successfully {} with ID: {}", NAME_OBJECT, id);
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

    @Transactional
    public PetUserRespAdminDto doActionPetUser(Long petUserId, Long userIdAuth, PetUserDoActionDto petUserDoActionDto) {
        log.debug("Do action in entity {} by ID: {}", NAME_OBJECT, petUserId);

        PetUserRespAdminDto findPetUserAdminDto = findPetUserById(petUserId);
        PetActionRespAdminDto findPetActionAdminDto = petActionServiceImpl.findPetActionById(petUserDoActionDto.petActionId());

        PetUserRespAdminDto modifMoodsAdminDto = rulesDoAction.doActionMoods(findPetUserAdminDto, findPetActionAdminDto);
        PetUserRespAdminDto modifAgeAdminDto = rulesDoAction.doActionCalculateAge(modifMoodsAdminDto, findPetActionAdminDto);
        PetUserRespAdminDto modifIsDieAdminDto = rulesDoAction.doActionCalculateIsDie(modifAgeAdminDto);

        PetUserRespAdminDto savedPetUserRespAdminDto = updatePetUser(petUserId, modifIsDieAdminDto, userIdAuth);

        log.debug("Save petUser for doAction {}: {}", NAME_OBJECT, savedPetUserRespAdminDto.name());


// TODO si esta muerto lanzar incidencia
        return savedPetUserRespAdminDto;
    }

    @Transactional
    public PetUserRespAdminDto doMovePetUser(Long petUserId, Long userIdAuth, PetUserDoMoveDto petUserDoMoveDto) {
        log.debug("Do action in entity {} by ID: {}", NAME_OBJECT, petUserId);

        PetUser findEntity = findById(petUserId);

        // TODO: gestion de que tiene que pasar en la accion

        log.debug("Exit action {}: {}", NAME_OBJECT, findEntity.getName());
        return petUserRespAdminDtoMapper.toDto(findEntity);
    }
}
