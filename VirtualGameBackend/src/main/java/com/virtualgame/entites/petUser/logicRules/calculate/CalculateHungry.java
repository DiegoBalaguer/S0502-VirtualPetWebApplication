package com.virtualgame.entites.petUser.logicRules.calculate;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateHungry {

    private final AppProperties appProperties;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;

    public PetUserRespAdminDto valueHungry(PetUserRespAdminDto petUserCalcDto, int valueHungry) {
        log.debug("Calculating Hungry.");
        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        int hungryMin = appProperties.getDefaultPetHungryMin();
        int hungryMax = appProperties.getDefaultPetHungryMax();
        int petHungry = petUserCalc.getHungry();
        int newHungry = petHungry + valueHungry;

        if ((petHungry >= hungryMax && valueHungry >= 0) || (petHungry <= hungryMin && valueHungry < 0)) {
            petUserCalc.setHungryReps(petUserCalc.getHungryReps() + 1);
            log.debug("Min or Max Hungry repetitions: " + petUserCalc.getHappyReps());
        } else {
            newHungry = Math.max(newHungry, hungryMin);
            newHungry = Math.min(newHungry, hungryMax);
            petUserCalc.setHungry(newHungry);
            petUserCalc.setHungryReps(0);
        }
        if (petUserCalc.getHungryReps() >= appProperties.getDefaultPetHungryDangerReps()) {
            log.debug("PetUser death for getHungryReps: {}", petUserCalc.getHungryReps());
            petUserCalc.setDeathDate(LocalDateTime.now());
        }
        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }
}
