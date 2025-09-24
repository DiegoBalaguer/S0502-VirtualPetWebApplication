package com.virtualgame.entites.petUser.logicRules;

import com.virtualgame.entites.petHabitat.PetHabitatServiceImpl;
import com.virtualgame.entites.petUser.dto.PetUserValidationDto;
import com.virtualgame.exception.exceptions.*;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetUserValidations {

    private final TranslationManagerService translate;
    private final PetHabitatServiceImpl  petHabitatServiceImpl;

    public void validationsPetUser(PetUserValidationDto dto) {
        petUserIsOfUser(dto.PetUserUserId(), dto.userIdAuth());

        petUserIsDeath(dto.deathDate(), dto.userIdAuth());

        petUserIsOldEnough(dto.age(), dto.ageMin(), dto.userIdAuth(), dto.action());
    }

    public void petUserIsOfUser(long petUserUserId, long userIdAuth) {
        if (petUserUserId != userIdAuth) {
            log.debug(translate
                    .getFormatSys("PetUser does not belong to the userId."));
            throw new PetUserNotBelongUser(translate.getFormatUsr("PetUser does not belong to the userId.", userIdAuth));
        }
    }

    public void petUserIsDeath(LocalDateTime deathDate, long userIdAuth) {
        if (deathDate != null) {
            log.debug(translate
                    .getFormatSys("PetUser dead, action cannot be performed."));
            throw new PetUserIsDieException(translate.getFormatUsr("PetUser dead, action cannot be performed.", userIdAuth));
        }
    }

    public void petUserIsOldEnough(int petUserAge, int ageMin, long userIdAuth, String action) {

        if (petUserAge < ageMin) {
            log.debug(translate
                    .getFormatSys("PetUser is not old enough for this {0}.", action));
            throw new PetUserAgeException(translate.getFormatUsr("PetUser is not old enough for this {0}.", userIdAuth, action));
        }
    }

    public void petUserAlreadyHabitat(long oldHabitatId, long newHabitatId, long userIdAuth) {
        if (oldHabitatId == newHabitatId) {
            log.debug(translate
                    .getFormatSys("PetUser already in habitat."));
            throw new PetUserAlreadyHabitatException(translate.getFormatUsr("PetUser already in habitat.", userIdAuth));
        }
    }

    public void petUserHabitatChangeAllowed(long petUserHabitatId, long newPetHabitatId, long userIdAuth) {
        if (!((petHabitatServiceImpl.isInDomedCity(petUserHabitatId) && petHabitatServiceImpl.isInDomedCity(newPetHabitatId))
                || (petHabitatServiceImpl.isInSanctuary(petUserHabitatId) && petHabitatServiceImpl.isInSanctuary(newPetHabitatId)))) {
            log.debug(translate
                    .getFormatSys("PetUser is prohibited from going to this habitat."));
            throw new PetUserForbiddenHabitatException(translate.getFormatUsr("PetUser is prohibited from going to this habitat.", userIdAuth));
        }
    }
}
