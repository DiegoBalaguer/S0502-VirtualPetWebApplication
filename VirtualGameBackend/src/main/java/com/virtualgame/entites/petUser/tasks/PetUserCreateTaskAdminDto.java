package com.virtualgame.entites.petUser.tasks;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petAction.PetActionServiceImpl;
import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import com.virtualgame.entites.petEntity.PetServiceImpl;
import com.virtualgame.entites.petEntity.dto.PetRespAdminDto;
import com.virtualgame.entites.petHabitat.PetHabitatServiceImpl;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import com.virtualgame.entites.petImage.PetImageServiceImpl;
import com.virtualgame.entites.petImage.dto.PetImageRequestDto;
import com.virtualgame.entites.petImage.dto.PetImageRespAdminDto;
import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespTaskAdminDto;
import com.virtualgame.translation.TranslationManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PetUserCreateTaskAdminDto {

    private final PetServiceImpl petServiceImpl;
    private final PetActionServiceImpl petActionServiceImpl;
    private final PetHabitatServiceImpl petHabitatServiceImpl;
    private final PetImageServiceImpl petImageServiceImpl;
    private final TranslationManagerService translate;
    private final AppProperties appProperties;


    @Transactional(readOnly = true)
    public PetUserRespTaskAdminDto getPetUserTaskAdminDto(PetUser petUser) {
        log.debug(translate
                .getSys("Getting full admin DTO for PetUserID: {0}", petUser.getId()));

        PetRespAdminDto petEntityDto = petServiceImpl.findPetEntityById(petUser.getPetTypeId());

        PetActionRespAdminDto petActionDto = petActionServiceImpl.findPetActionById(petUser.getPetActionId());

        PetHabitatRespAdminDto petHabitatDto = petHabitatServiceImpl.findPetHabitatById(petUser.getPetHabitatId());

        PetImageRequestDto petImageRequestDto = new PetImageRequestDto(petUser.getPetTypeId(), petUser.getAge());

        PetImageRespAdminDto petImageDto = petImageServiceImpl.findPetImageUrlForAgeByIdAndAge(petImageRequestDto);

        Integer happyRepsMax = appProperties.getDefaultPetHappyDangerReps();
        Integer tiredMax = appProperties.getDefaultPetTiredDangerReps();
        Integer hungryRepsMax = appProperties.getDefaultPetHungryDangerReps();

        return new PetUserRespTaskAdminDto(
                petUser.getId(),
                petUser.getName(),
                petUser.getPetTypeId(),
                petEntityDto.name(), // Nombre del PetType
                petUser.getUserId(),
                petImageDto.imageUrl(), // nueva imagen
                petUser.getPetActionId(),
                petActionDto.name(), // Nombre de la Acción
                petUser.getPetHabitatId(),
                petHabitatDto.name(), // Nombre del Hábitat
                petHabitatDto.parentId(),
                petHabitatDto.imageUrl(), // ImageUrl del Hábitat
                petUser.getHappy(),
                petUser.getHappyReps(),
                happyRepsMax, // Máximo
                petUser.getTired(),
                petUser.getTiredReps(),
                tiredMax, // Máximo
                petUser.getHungry(),
                petUser.getHungryReps(),
                hungryRepsMax, // Máximo
                petUser.getMonths(),
                petUser.getAge(),
                petUser.getDeathDate(),
                petUser.getDeathReason()
        );
    }
}
