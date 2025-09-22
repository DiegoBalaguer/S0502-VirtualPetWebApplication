package com.virtualgame.entites.petUser.logicRules;

import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRuleStatusDto;
import com.virtualgame.entites.petUser.logicRules.calculate.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetUserRulesCalculate {

    private final CalculateHappy calculateHappy;
    private final CalculateTired calculateTired;
    private final CalculateHungry calculateHungry;
    private final CalculateAge calculateAge;
    private final CalculateIsDie calculateIsDie;

    public PetUserRespAdminDto doActionMoods(PetUserRespAdminDto petUserRespAdminDto, PetUserRuleStatusDto ruleStatusDto) {
        PetUserRespAdminDto happyAdminDto = calculateHappy.valueHappy(petUserRespAdminDto, ruleStatusDto.happy());
        PetUserRespAdminDto tiredAdminDto = calculateTired.valueTired(happyAdminDto, ruleStatusDto.tired());
        return calculateHungry.valueHungry(tiredAdminDto, ruleStatusDto.hungry());
    }

    public PetUserRespAdminDto doActionCalculateAge(PetUserRespAdminDto petUserRespAdminDto, PetUserRuleStatusDto ruleStatusDto) {
        return calculateAge.valueAge(petUserRespAdminDto, ruleStatusDto.months(), ruleStatusDto.age());
    }

    public PetUserRespAdminDto doActionCalculateIsDie(PetUserRespAdminDto petUserRespAdminDto) {
        return calculateIsDie.valueIsDie(petUserRespAdminDto);
    }

}
