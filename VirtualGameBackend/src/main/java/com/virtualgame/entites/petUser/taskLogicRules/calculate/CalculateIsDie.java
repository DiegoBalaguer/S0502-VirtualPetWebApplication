package com.virtualgame.entites.petUser.taskLogicRules.calculate;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petHabitat.PetHabitatServiceImpl;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.tasks.PetUserCreateTaskAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRespTaskAdminDto;
import com.virtualgame.security.user.auth.CurrentUserService;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateIsDie {

    private final AppProperties appProperties;
    private final PetHabitatServiceImpl petHabitatServiceImpl;
    private final TranslationManagerService translate;
    private final CurrentUserService currentUserService;
    private final PetUserCreateTaskAdminDto petUserCreateTaskAdminDto;

    public void valueIsDie(PetUser petUserCalc) {
        log.debug(translate
                .getSys("Calculating is die."));
        log.debug(translate
                .getSys("PetUserCalc: {0}", petUserCalc));


        PetUserRespTaskAdminDto getPetUserTaskAdminDto = petUserCreateTaskAdminDto.getPetUserTaskAdminDto(petUserCalc);

        log.debug(translate
                .getSys("Calculating is die i the if's."));

        if (petHabitatServiceImpl.isInDomedCity(getPetUserTaskAdminDto.petHabitatId())) {
            if (petUserCalc.getAge() >= appProperties.getDefaultPetAgeDieDomedCity()) {
                log.debug(translate
                        .getSys("PetUser death for AgeDieDomedCity with {0} years old.", petUserCalc.getAge()));
                petUserSetDie(petUserCalc);
                petUserCalc.setDeathReason(translate.getUsr(
                        currentUserService.getCurrentUserLanguageCode(),
                        "PetUser death for AgeDieDomedCity with {0} years old." + petUserCalc.getAge()));
                petUserCalc.setPetHabitatId(appProperties.getDefaultPetHabitatCarrouselId());
            } else if (Objects.equals(petUserCalc.getPetHabitatId(), appProperties.getDefaultPetHabitatCarrouselId())) {
                log.debug(translate
                        .getSys("PetUser death for suicide in DomedCity with {0} years old.", petUserCalc.getAge()));
                petUserSetDie(petUserCalc);
                petUserCalc.setDeathReason(translate.getUsr(
                        currentUserService.getCurrentUserLanguageCode(),
                        "PetUser death for suicide in DomedCity with {0} years old.", petUserCalc.getAge()));
            }
        } else if (Objects.equals(petUserCalc.getPetHabitatId(), appProperties.getDefaultPetHabitatCementerId())) {
            log.debug(translate
                    .getSys("PetUser death for suicide in Cementer with {0} years old.", petUserCalc.getAge()));
            petUserSetDie(petUserCalc);
            petUserCalc.setDeathReason(translate.getUsr(
                    currentUserService.getCurrentUserLanguageCode(),
                    "PetUser death for suicide in Cementer with {0} years old.", petUserCalc.getAge()));
        } else {
            int probabilityAge = randomAge(appProperties.getDefaultPetAgeDieMinOutside(), appProperties.getDefaultPetAgeDieMaxOutside());
            if (petUserCalc.getAge() >= probabilityAge) {
                log.debug(translate
                        .getSys("PetUser death with {0} years old.", petUserCalc.getAge()));
                petUserSetDie(petUserCalc);
                petUserCalc.setDeathReason(translate.getUsr(
                        currentUserService.getCurrentUserLanguageCode(),
                        "PetUser death with {0} years old.", petUserCalc.getAge()));
                petUserCalc.setPetHabitatId(appProperties.getDefaultPetHabitatCementerId());
            }
        }
        log.debug(translate
                .getSys("close method"));
    }

    private int randomAge(int num_min, int num_max) {
        Random random = new Random();
        return random.nextInt(num_min, num_max + 1);
    }

    private void petUserSetDie(PetUser petUserCalc) {
        petUserCalc.setDeathDate(LocalDateTime.now());
    }
}
