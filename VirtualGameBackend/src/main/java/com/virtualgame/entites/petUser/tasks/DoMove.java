package com.virtualgame.entites.petUser.tasks;

import com.virtualgame.entites.petHabitat.PetHabitatServiceImpl;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.PetUserServiceImpl;
import com.virtualgame.entites.petUser.dto.*;
import com.virtualgame.entites.petUser.taskLogicRules.PetUserRules;
import com.virtualgame.entites.petUser.taskLogicRules.PetUserValidations;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import com.virtualgame.entites.petUser.mapper.PetUserRespTaskAdminDtoMapper;
import com.virtualgame.entites.petUser.mapper.PetUserRuleStatusDtoMapper;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class DoMove {

    private final PetUserServiceImpl petUserServiceImpl;
    private final PetHabitatServiceImpl petHabitatServiceImpl;
    private final PetUserValidations petUserValidations;
    private final PetUserRules petUserRules;

    private final PetUserRuleStatusDtoMapper petUserRuleStatusDtoMapper;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;
    private final PetUserRespTaskAdminDtoMapper petUserRespTaskAdminDtoMapper;

    private final TranslationManagerService translate;
    private static final String NAME_OBJECT = "pet user entity";

    @Transactional
    public PetUserRespTaskAdminDto doMovePetUser(PetUserTaskRequestUserDto taskDto, Long userIdAuth) {
        Long petUserId = taskDto.petUserId();
        Long newPetHabitatId = taskDto.petUserTaskId();
        log.debug(translate
                .getSys("Do move in entity {0} by PetUserID: {1} and petHabitatId {2}", NAME_OBJECT, petUserId, newPetHabitatId));

        PetUserRespTaskAdminDto petUserTaskAdminDto = petUserServiceImpl.findPetUserById(petUserId);
        PetHabitatRespAdminDto newHabitatDto = petHabitatServiceImpl.findPetHabitatById(newPetHabitatId);

        validationsMove(taskDto, petUserTaskAdminDto, newHabitatDto, userIdAuth);

        PetUser loadPetUser = petUserRespTaskAdminDtoMapper.toEntity(petUserTaskAdminDto);
        loadPetUser.setPetHabitatId(newPetHabitatId);
        PetUserRespTaskAdminDto updatedPetUserTaskAdminDto =
                petUserServiceImpl.updatePetUser(petUserId, petUserRespAdminDtoMapper.toDto(loadPetUser), userIdAuth);

        PetUserRespAdminDto modifyAdminDto = calculateMoveCost(taskDto, newHabitatDto, updatedPetUserTaskAdminDto);

        log.debug(translate
                .getSys("Save petUser for doMove {0}: {1}", NAME_OBJECT, modifyAdminDto.name()));

        return petUserServiceImpl.updatePetUser(petUserId, modifyAdminDto, userIdAuth);
    }

    private void validationsMove(PetUserTaskRequestUserDto taskDto, PetUserRespTaskAdminDto petUserTaskAdminDto, PetHabitatRespAdminDto newHabitatDto, Long userIdAuth) {
        PetUserValidationDto validationDto = new PetUserValidationDto(userIdAuth,
                petUserTaskAdminDto.id(), petUserTaskAdminDto.deathDate(), petUserTaskAdminDto.age(), newHabitatDto.ageMin(), "habitat");


        petUserValidations.validationsPetUser(validationDto);

        petUserValidations.petUserAlreadyHabitat(petUserTaskAdminDto.petHabitatId(), taskDto.petUserTaskId(), userIdAuth);

        petUserValidations.petUserHabitatChangeAllowed(petUserTaskAdminDto.petHabitatId(), taskDto.petUserTaskId(), userIdAuth);
    }

    private PetUserRespAdminDto calculateMoveCost(PetUserTaskRequestUserDto taskDto, PetHabitatRespAdminDto newHabitatDto, PetUserRespTaskAdminDto updatedPetUserTaskAdminDto) {
        Long newPetHabitatId = taskDto.petUserTaskId();

        PetUserRespAdminDto findPetUserAdminDto = petUserRespAdminDtoMapper.toDtoByTaskDto(updatedPetUserTaskAdminDto);

        PetUserRuleStatusDto ruleStatusDto = petUserRuleStatusDtoMapper.toDtoFromHabitatDto(newHabitatDto);

        return petUserRules.updateValues(findPetUserAdminDto, ruleStatusDto);
    }
}
