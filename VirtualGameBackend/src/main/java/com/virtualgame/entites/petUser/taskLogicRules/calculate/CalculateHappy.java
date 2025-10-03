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
public class CalculateHappy {

    private final AppProperties appProperties;
    private final TranslationManagerService translate;
    private final CurrentUserService currentUserService;

    public void valueHappy(PetUser petUserCalc, int valueHappy) {
        log.debug(translate
                .getSys("Calculating happy."));
        int happyMin = appProperties.getDefaultPetHappyMin();
        int happyMax = appProperties.getDefaultPetHappyMax();
        int petHappy = petUserCalc.getHappy();
        int newHappy = petHappy + valueHappy;

        if ((petHappy >= happyMax && valueHappy > 0) || (petHappy <= happyMin && valueHappy <= 0)) {
            petUserCalc.setHappyReps(petUserCalc.getHappyReps() + 1);
            log.debug(translate
                    .getSys("Min or Max Happy repetitions: {0}", petUserCalc.getHappyReps()));
        } else {
            newHappy = Math.max(newHappy, happyMin);
            newHappy = Math.min(newHappy, happyMax);
            petUserCalc.setHappy(newHappy);
            petUserCalc.setHappyReps(0);
        }

        if (petUserCalc.getHappyReps() >= appProperties.getDefaultPetHappyDangerReps()) {
            log.debug(translate
                    .getSys("PetUser death for getHappyReps: {0}", petUserCalc.getHappyReps()));
            petUserCalc.setDeathDate(LocalDateTime.now());

            petUserCalc.setDeathReason(translate.getUsr(
                    currentUserService.getCurrentUserLanguageCode(),
                    "PetUser death for getHappyReps: {0]", petUserCalc.getHappyReps()));
        }
    }
}
