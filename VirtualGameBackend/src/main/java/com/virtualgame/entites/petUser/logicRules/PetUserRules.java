package com.virtualgame.entites.petUser.logicRules;

import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRuleStatusDto;
import com.virtualgame.entites.petUser.logicRules.calculate.*;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
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

    public PetUserRespAdminDto updateValues(PetUserRespAdminDto petUserRespAdminDto, PetUserRuleStatusDto ruleStatusDto) {

        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserRespAdminDto);
        doActionMoods(petUserCalc, ruleStatusDto);
        doActionCalculateAge(petUserCalc, ruleStatusDto);
        doActionCalculateIsDie(petUserCalc);

        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    private void doActionMoods(PetUser petUserCalc, PetUserRuleStatusDto ruleStatusDto) {
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
}
