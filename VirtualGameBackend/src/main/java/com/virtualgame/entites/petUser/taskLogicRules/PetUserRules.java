package com.virtualgame.entites.petUser.taskLogicRules;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRuleStatusDto;
import com.virtualgame.entites.petUser.taskLogicRules.calculate.*;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import com.virtualgame.security.user.auth.CurrentUserService;
import com.virtualgame.security.user.auth.dto.AuthSecurityUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetUserRules {

    private final CalculateHappy calculateHappy;
    private final CalculateTired calculateTired;
    private final CalculateHungry calculateHungry;
    private final CalculateAge calculateAge;
    private final CalculateIsDie calculateIsDie;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;
    private final AppProperties appProperties;
    private final CurrentUserService currentUserService;

    public PetUserRespAdminDto updateValues(PetUserRespAdminDto petUserRespAdminDto, PetUserRuleStatusDto ruleStatusDto) {

        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserRespAdminDto);
        doActionMoods(petUserCalc, ruleStatusDto);
        doActionCalculateAge(petUserCalc, ruleStatusDto);
        doActionCalculateIsDie(petUserCalc);

        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    private void doActionMoods(PetUser petUserCalc, PetUserRuleStatusDto ruleStatusDto) {
        AuthSecurityUserDto securityUser =   currentUserService.getCurrentUserDto();
        calculateHappy.valueHappy(petUserCalc, ruleStatusDto.happy());
        calculateTired.valueTired(petUserCalc, ruleStatusDto.tired());
        calculateHungry.valueHungry(petUserCalc, ruleStatusDto.hungry());
    }

    public void doActionCalculateAge(PetUser petUserCalc, PetUserRuleStatusDto ruleStatusDto) {
        calculateAge.valueAge(petUserCalc, ruleStatusDto.months(), ruleStatusDto.age());
    }

    public void doActionCalculateIsDie(PetUser petUserCalc) {
        calculateIsDie.valueIsDie(petUserCalc);
    }

    public PetUserRespAdminDto changeHabitatForScapeOrReturn(PetUserRespAdminDto savedDto, Long actionId) {

        PetUser petUser = petUserRespAdminDtoMapper.toEntity(savedDto);

        if (appProperties.getDefaultPetActionScape().equals(actionId)) {
            petUser.setPetHabitatId(appProperties.getDefaultPetHabitatEscapeId());
        } else if (appProperties.getDefaultPetActionReturn().equals(actionId)) {
            petUser.setPetHabitatId(appProperties.getDefaultPetHabitatReturnId());
        }

        return petUserRespAdminDtoMapper.toDto(petUser);
    }
}
