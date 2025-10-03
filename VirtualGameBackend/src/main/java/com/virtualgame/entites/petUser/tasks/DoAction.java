package com.virtualgame.entites.petUser.tasks;

import com.virtualgame.entites.petAction.PetActionServiceImpl;
import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
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
public class DoAction {

    private final PetUserServiceImpl petUserServiceImpl;
    private final PetActionServiceImpl  petActionServiceImpl;
    private final PetUserValidations petUserValidations;
    private final PetUserRules petUserRules;

    private final PetUserRuleStatusDtoMapper  petUserRuleStatusDtoMapper;
    private final PetUserRespTaskAdminDtoMapper petUserRespTaskAdminDtoMapper;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;

    private final TranslationManagerService translate;
    private static final String NAME_OBJECT = "pet user entity";

    @Transactional
    public PetUserRespTaskAdminDto doActionPetUser(PetUserTaskRequestUserDto taskDto, Long userIdAuth) {
        Long petUserId = taskDto.petUserId();
        Long newPetActionId = taskDto.petUserTaskId();
        log.debug(translate
                .getSys("Do action in entity {0} by PetUserID: {1} and petActionId {2}", NAME_OBJECT, petUserId, newPetActionId));

        PetUserRespTaskAdminDto petUserTaskAdminDto=petUserServiceImpl.findPetUserById(petUserId);
        PetActionRespAdminDto newActionDto = petActionServiceImpl.findPetActionById(newPetActionId);

        validationsAction(petUserTaskAdminDto,newActionDto, userIdAuth);

        PetUserRespAdminDto modifyAdminDto =  calculateActionsCost(taskDto, newActionDto, petUserTaskAdminDto);

       log.debug(translate
                .getSys("Save petUser for doAction {0}: {1}", NAME_OBJECT, modifyAdminDto.name()));
        return petUserServiceImpl.updatePetUser(petUserId, modifyAdminDto, userIdAuth);
    }

    private void validationsAction(PetUserRespTaskAdminDto petUserTaskAdminDto, PetActionRespAdminDto newActionDto, Long userIdAuth) {
        PetUserValidationDto validationDto = new PetUserValidationDto(userIdAuth,
                petUserTaskAdminDto.id(), petUserTaskAdminDto.deathDate(), petUserTaskAdminDto.age(), newActionDto.ageMin(), "action");
        petUserValidations.validationsPetUser(validationDto);
    }

    private PetUserRespAdminDto calculateActionsCost(PetUserTaskRequestUserDto taskDto, PetActionRespAdminDto newActionDto, PetUserRespTaskAdminDto updatedPetUserTaskAdminDto) {

        PetUserRespAdminDto findPetUserAdminDto = petUserRespAdminDtoMapper.toDtoByTaskDto(updatedPetUserTaskAdminDto);

        PetUserRuleStatusDto ruleStatusDto = petUserRuleStatusDtoMapper.toDtoFromActionDto(newActionDto);

        PetUserRespAdminDto modifyAdminDto = petUserRules.updateValues(findPetUserAdminDto, ruleStatusDto);

        return petUserRules.changeHabitatForScapeOrReturn(modifyAdminDto, taskDto.petUserTaskId());
    }
}
