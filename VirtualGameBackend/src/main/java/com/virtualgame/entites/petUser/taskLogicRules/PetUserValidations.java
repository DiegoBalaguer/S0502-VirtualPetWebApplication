package com.virtualgame.entites.petUser.taskLogicRules;

import com.virtualgame.entites.petHabitat.PetHabitatServiceImpl;
import com.virtualgame.entites.petUser.dto.PetUserValidationDto;
import com.virtualgame.exception.exceptions.*;
import com.virtualgame.security.user.auth.CurrentUserService;
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
    private final CurrentUserService currentUserService;
    private final PetHabitatServiceImpl  petHabitatServiceImpl;

    public void validationsPetUser(PetUserValidationDto dto) {
        petUserIsOfUser(dto.PetUserUserId(), dto.userIdAuth());

        petUserIsDeath(dto.deathDate(), dto.userIdAuth());

        petUserIsOldEnough(dto.age(), dto.ageMin(), dto.userIdAuth(), dto.action());
    }

    public void petUserIsOfUser(long petUserUserId, long userIdAuth) {
        if (petUserUserId != userIdAuth) {
            log.debug(translate
                    .getSys("PetUser does not belong to the userId."));
            String languageCode = currentUserService.getCurrentUserLanguageCode();
            throw new PetUserNotBelongUser(translate.getUsr(languageCode, "PetUser does not belong to the userId."));
        }
    }

    public void petUserIsDeath(LocalDateTime deathDate, long userIdAuth) {
        if (deathDate != null) {
            log.debug(translate
                    .getSys("PetUser dead, action cannot be performed."));
            String languageCode = currentUserService.getCurrentUserLanguageCode();
            throw new PetUserIsDieException(translate.getUsr(languageCode, "PetUser dead, action cannot be performed."));
        }
    }

    public void petUserIsOldEnough(int petUserAge, int ageMin, long userIdAuth, String action) {
        if (petUserAge < ageMin) {
            log.debug(translate
                    .getSys("PetUser is not old enough for this {0}.", action));
            String languageCode = currentUserService.getCurrentUserLanguageCode();
            throw new PetUserAgeException(translate.getUsr(languageCode, "PetUser is not old enough for this {0}.", action));
        }
    }

    public void petUserAlreadyHabitat(long oldHabitatId, long newHabitatId, long userIdAuth) {
        if (oldHabitatId == newHabitatId) {
            log.debug(translate
                    .getSys("PetUser already in habitat."));
            String languageCode = currentUserService.getCurrentUserLanguageCode();
            throw new PetUserAlreadyHabitatException(translate.getUsr(languageCode, "PetUser already in habitat."));
        }
    }

    public void petUserHabitatChangeAllowed(long petUserHabitatId, long newPetHabitatId, long userIdAuth) {
        if (!((petHabitatServiceImpl.isInDomedCity(petUserHabitatId) && petHabitatServiceImpl.isInDomedCity(newPetHabitatId))
                || (petHabitatServiceImpl.isInSanctuary(petUserHabitatId) && petHabitatServiceImpl.isInSanctuary(newPetHabitatId)))) {
     // TODO: USAR ESTO PARA TRADUCCIONES
            log.debug(translate
                    .getSys("PetUser is prohibited from going to this habitat."));
            String languageCode = currentUserService.getCurrentUserLanguageCode();
            throw new PetUserForbiddenHabitatException(translate
                    .getUsr(languageCode, "PetUser is prohibited from going to this habitat."));
        }
    }
}
