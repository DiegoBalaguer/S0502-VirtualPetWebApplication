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
public class CalculateHappy {

    private final AppProperties appProperties;

    public void valueHappy(PetUser petUserCalc, int valueHappy) {
        log.debug("Calculating happy.");
        //PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        int happyMin = appProperties.getDefaultPetHappyMin();
        int happyMax = appProperties.getDefaultPetHappyMax();
        int petHappy = petUserCalc.getHappy();
        int newHappy = petHappy + valueHappy;

        if ((petHappy >= happyMax && valueHappy > 0) || (petHappy <= happyMin && valueHappy <= 0)) {
            petUserCalc.setHappyReps(petUserCalc.getHappyReps() + 1);
            log.debug("Min or Max Happy repetitions: " + petUserCalc.getHappyReps());
        } else {
            newHappy = Math.max(newHappy, happyMin);
            newHappy = Math.min(newHappy, happyMax);
            petUserCalc.setHappy(newHappy);
            petUserCalc.setHappyReps(0);
        }

        if (petUserCalc.getHappyReps() >= appProperties.getDefaultPetHappyDangerReps()) {
            log.debug("PetUser death for getHappyReps: {}", petUserCalc.getHappyReps());
            petUserCalc.setDeathDate(LocalDateTime.now());
        }
        //return petUserRespAdminDtoMapper.toDto(petUserCalc);
        //return petUserCalc;
    }
}
