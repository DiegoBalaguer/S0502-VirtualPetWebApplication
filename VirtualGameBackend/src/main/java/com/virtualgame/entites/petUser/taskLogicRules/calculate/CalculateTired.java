package com.virtualgame.entites.petUser.taskLogicRules.calculate;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.mapper.PetUserRespAdminDtoMapper;
import com.virtualgame.security.user.auth.CurrentUserService;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateTired {

    private final AppProperties appProperties;
    private final TranslationManagerService translate;
    private final CurrentUserService currentUserService;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;

    public void valueTired(PetUser petUserCalc, int valueTired) {
        log.debug(translate
                .getSys("Calculating Tired."));

        int tiredMin = appProperties.getDefaultPetTiredMin();
        int tiredMax = appProperties.getDefaultPetTiredMax();
        int petTired = petUserCalc.getTired();
        int newTired = petTired + valueTired;

        if ((petTired >= tiredMax && valueTired >= 0) || (petTired <= tiredMin && valueTired < 0)) {
            petUserCalc.setTiredReps(petUserCalc.getTiredReps() + 1);
            log.debug(translate
                    .getSys("Min or Max Tired repetitions: {0}", petUserCalc.getHappyReps()));
        } else {
            newTired = Math.max(newTired, tiredMin);
            newTired = Math.min(newTired, tiredMax);
            petUserCalc.setTired(newTired);
            petUserCalc.setTiredReps(0);
        }
        if (petUserCalc.getTiredReps() >= appProperties.getDefaultPetTiredDangerReps()) {
            log.debug(translate
                    .getSys("PetUser death for getTiredReps: {0}", petUserCalc.getTiredReps()));
            petUserCalc.setDeathDate(LocalDateTime.now());

            petUserCalc.setDeathReason(translate.getUsr(
                    currentUserService.getCurrentUserLanguageCode(),
                    "PetUser death for getTiredReps: {0}", petUserCalc.getTiredReps()));
        }
    }
}
