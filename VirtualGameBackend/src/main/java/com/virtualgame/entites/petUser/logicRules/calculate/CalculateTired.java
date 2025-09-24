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
public class CalculateTired {

    private final AppProperties appProperties;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;

    public void valueTired(PetUser petUserCalc, int valueTired) {
        log.debug("Calculating Tired.");
        //PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        int tiredMin = appProperties.getDefaultPetTiredMin();
        int tiredMax = appProperties.getDefaultPetTiredMax();
        int petTired = petUserCalc.getTired();
        int newTired = petTired + valueTired;

        if ((petTired >= tiredMax && valueTired >= 0) || (petTired <= tiredMin && valueTired < 0)) {
            petUserCalc.setTiredReps(petUserCalc.getTiredReps() + 1);
            log.debug("Min or Max Tired repetitions: " + petUserCalc.getHappyReps());
        } else {
            newTired = Math.max(newTired, tiredMin);
            newTired = Math.min(newTired, tiredMax);
            petUserCalc.setTired(newTired);
            petUserCalc.setTiredReps(0);
        }
        if (petUserCalc.getTiredReps() >= appProperties.getDefaultPetTiredDangerReps()) {
            log.debug("PetUser death for getTiredReps: {}", petUserCalc.getTiredReps());
            petUserCalc.setDeathDate(LocalDateTime.now());
        }
        //return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }
}
