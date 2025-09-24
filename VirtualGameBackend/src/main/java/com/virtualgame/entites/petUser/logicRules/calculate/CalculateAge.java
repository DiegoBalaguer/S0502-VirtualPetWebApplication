package com.virtualgame.entites.petUser.logicRules.calculate;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateAge {

    private final AppProperties appProperties;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;

    public void valueAge(PetUser petUserCalc, int valueMonths, int valueAge) {
        log.debug("Calculating Months age for pet.");
        //PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        Integer newMonths = petUserCalc.getMonths() + valueMonths;
        if (newMonths >= appProperties.getDefaultPetMouthsToAge()) {
            petUserCalc.setMonths(newMonths - appProperties.getDefaultPetMouthsToAge());
            petUserCalc.setAge(petUserCalc.getAge() + 1);
        } else {
            petUserCalc.setMonths(newMonths);
        }
        petUserCalc.setAge(petUserCalc.getAge() + valueAge);
        //return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }
}
