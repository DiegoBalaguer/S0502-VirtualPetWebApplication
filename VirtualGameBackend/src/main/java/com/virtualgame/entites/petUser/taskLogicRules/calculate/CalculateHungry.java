package com.virtualgame.entites.petUser.taskLogicRules.calculate;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.security.user.auth.CurrentUserService;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculateHungry {

    private final AppProperties appProperties;
    private final TranslationManagerService translate;
    private final CurrentUserService currentUserService;

    public void valueHungry(PetUser petUserCalc, int valueHungry) {
        log.debug(translate
                .getSys("Calculating Hungry."));

        int hungryMin = appProperties.getDefaultPetHungryMin();
        int hungryMax = appProperties.getDefaultPetHungryMax();
        int petHungry = petUserCalc.getHungry();
        int newHungry = petHungry + valueHungry;

        if ((petHungry >= hungryMax && valueHungry >= 0) || (petHungry <= hungryMin && valueHungry < 0)) {
            petUserCalc.setHungryReps(petUserCalc.getHungryReps() + 1);
            log.debug(translate
                    .getSys("Min or Max Hungry repetitions: {0}", petUserCalc.getHappyReps()));
        } else {
            newHungry = Math.max(newHungry, hungryMin);
            newHungry = Math.min(newHungry, hungryMax);
            petUserCalc.setHungry(newHungry);
            petUserCalc.setHungryReps(0);
        }
        if (petUserCalc.getHungryReps() >= appProperties.getDefaultPetHungryDangerReps()) {
            log.debug(translate
                    .getSys("PetUser death for getHungryReps: {0}", petUserCalc.getHungryReps()));
            petUserCalc.setDeathDate(LocalDateTime.now());
            petUserCalc.setDeathReason(translate.getUsr(
                    currentUserService.getCurrentUserLanguageCode(),
                    "PetUser death for getHungryReps: {0}", petUserCalc.getHungryReps()));
        }
    }
}
