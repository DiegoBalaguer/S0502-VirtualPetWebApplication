package com.virtualgame.logicRules;

import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RulesDoAction {

    private final MoodsCalculate moodsCalculate;

    public PetUserRespAdminDto doActionMoods(PetUserRespAdminDto petUserRespAdminDto, PetActionRespAdminDto petActionRespAdminDto) {

        if (petUserRespAdminDto.age() >= petActionRespAdminDto.age()) {
            PetUserRespAdminDto happyAdminDto = moodsCalculate.calculateHappy(petUserRespAdminDto, petActionRespAdminDto.happy());
            PetUserRespAdminDto tiredAdminDto = moodsCalculate.calculateTired(happyAdminDto, petActionRespAdminDto.tired());
            return moodsCalculate.calculateHungry(tiredAdminDto, petActionRespAdminDto.hungry());
        }
        return petUserRespAdminDto;
    }

    public PetUserRespAdminDto doActionCalculateAge(PetUserRespAdminDto petUserRespAdminDto, PetActionRespAdminDto petActionRespAdminDto) {
        return moodsCalculate.calculateMonthsAge(petUserRespAdminDto, petActionRespAdminDto.months(), petActionRespAdminDto.age());
    }

    public PetUserRespAdminDto doActionCalculateIsDie(PetUserRespAdminDto petUserRespAdminDto) {
        return moodsCalculate.calculateIsDie(petUserRespAdminDto);
    }

}
