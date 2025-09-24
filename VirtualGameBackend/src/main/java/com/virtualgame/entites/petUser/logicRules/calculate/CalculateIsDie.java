package com.virtualgame.entites.petUser.logicRules.calculate;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petHabitat.PetHabitat;
import com.virtualgame.entites.petHabitat.PetHabitatServiceImpl;
import com.virtualgame.entites.petHabitat.mapper.PetHabitatRespAdminDtoMapper;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateIsDie {

    private final AppProperties appProperties;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;
    private final PetHabitatRespAdminDtoMapper petHabitatRespAdminDtoMapper;
    private final PetHabitatServiceImpl petHabitatServiceImpl;

    public void valueIsDie(PetUser petUserCalc) {
        log.debug("Calculating is die.");
        //PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        PetHabitat petHabitat = petHabitatRespAdminDtoMapper.toEntity(
                petHabitatServiceImpl.findPetHabitatById(petUserCalc.getPetHabitatId()));

        if (petHabitatServiceImpl.isInDomedCity(petHabitat.getId())) {
            if (petUserCalc.getAge() >= appProperties.getDefaultPetAgeDieDomedCity()) {
                log.debug("PetUser death for AgeDieDomedCity: {}", petUserCalc.getAge());
                petUserSetDie(petUserCalc);
                petUserCalc.setPetHabitatId(appProperties.getDefaultPetHabitatCarrouselId());
            }
        } else  {
          int probabilityAge = randomAge(appProperties.getDefaultPetAgeDieMinOutside(), appProperties.getDefaultPetAgeDieMaxOutside());
          if (petUserCalc.getAge() >= probabilityAge) {
              log.debug("PetUser death for Age: {}", petUserCalc.getAge());
              petUserSetDie(petUserCalc);
              petUserCalc.setPetHabitatId(appProperties.getDefaultPetHabitatCementerId());
          }
        }
        //return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    private int randomAge(int num_min, int num_max) {
        Random random = new Random();
        return random.nextInt(num_min, num_max + 1);
    }

    private void petUserSetDie(PetUser petUserCalc) {
        petUserCalc.setDeathDate(LocalDateTime.now());
    }
}
