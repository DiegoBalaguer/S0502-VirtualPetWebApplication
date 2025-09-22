package com.virtualgame.logicRules;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class MoodsCalculate {

    private final AppProperties appProperties;
    private final PetUserRespAdminDtoMapper petUserRespAdminDtoMapper;
    private final PetHabitatRespAdminDtoMapper petHabitatRespAdminDtoMapper;
    private final PetHabitatServiceImpl petHabitatServiceImpl;

    public PetUserRespAdminDto calculateHappy(PetUserRespAdminDto petUserCalcDto, int valueHappy) {
        log.debug("Calculating happy for pet action.");
        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        int happyMin = appProperties.getDefaultPetHappyMin();
        int happyMax = appProperties.getDefaultPetHappyMax();
        int petHappy = petUserCalc.getHappy();
        int newHappy = petHappy + valueHappy;

        if ((petHappy >= happyMax && valueHappy > 0) || (petHappy <= happyMin && valueHappy <= 0)) {
            petUserCalc.setHappyReps(petUserCalc.getHappyReps() + 1);
            log.debug("Min or Max Happy for pet action repetitions: " + petUserCalc.getHappyReps());
        } else {
            newHappy = Math.max(newHappy, happyMin);
            newHappy = Math.min(newHappy, happyMax);
            petUserCalc.setHappy(newHappy);
            petUserCalc.setHappyReps(0);
        }

        if (petUserCalc.getHappyReps() >= appProperties.getDefaultPetHappyDangerReps()) {
            log.debug("PetUser death for getHappyReps: {}", petUserCalc.getHappyReps());
            petUserSetDie(petUserCalc);
        }
        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    public PetUserRespAdminDto calculateTired(PetUserRespAdminDto petUserCalcDto, int valueTired) {
        log.debug("Calculating Tired for pet action.");
        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        int tiredMin = appProperties.getDefaultPetTiredMin();
        int tiredMax = appProperties.getDefaultPetTiredMax();
        int petTired = petUserCalc.getTired();
        int newTired = petTired + valueTired;

        if ((petTired >= tiredMax && valueTired >= 0) || (petTired <= tiredMin && valueTired < 0)) {
            petUserCalc.setTiredReps(petUserCalc.getTiredReps() + 1);
            log.debug("Min or Max Tired for pet action repetitions: " + petUserCalc.getHappyReps());
        } else {
            newTired = Math.max(newTired, tiredMin);
            newTired = Math.min(newTired, tiredMax);
            petUserCalc.setTired(newTired);
            petUserCalc.setTiredReps(0);
        }
        if (petUserCalc.getTiredReps() >= appProperties.getDefaultPetTiredDangerReps()) {
            log.debug("PetUser death for getTiredReps: {}", petUserCalc.getTiredReps());
            petUserSetDie(petUserCalc);
        }
        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    public PetUserRespAdminDto calculateHungry(PetUserRespAdminDto petUserCalcDto, int valueHungry) {
        log.debug("Calculating Hungry for pet action.");
        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        int hungryMin = appProperties.getDefaultPetHungryMin();
        int hungryMax = appProperties.getDefaultPetHungryMax();
        int petHungry = petUserCalc.getHungry();
        int newHungry = petHungry + valueHungry;

        if ((petHungry >= hungryMax && valueHungry >= 0) || (petHungry <= hungryMin && valueHungry < 0)) {
            petUserCalc.setHungryReps(petUserCalc.getHungryReps() + 1);
            log.debug("Min or Max HHungry for pet action repetitions: " + petUserCalc.getHappyReps());
        } else {
            newHungry = Math.max(newHungry, hungryMin);
            newHungry = Math.min(newHungry, hungryMax);
            petUserCalc.setHungry(newHungry);
            petUserCalc.setHungryReps(0);
        }
        if (petUserCalc.getHungryReps() >= appProperties.getDefaultPetHungryDangerReps()) {
            log.debug("PetUser death for getHungryReps: {}", petUserCalc.getHungryReps());
            petUserSetDie(petUserCalc);
        }
        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    public PetUserRespAdminDto calculateMonthsAge(PetUserRespAdminDto petUserCalcDto, int valueMonths, int valueAge) {
        log.debug("Calculating Months age for pet.");
        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        Integer newMonths = petUserCalc.getMonths() + valueMonths;
        if (newMonths >= appProperties.getDefaultPetMouthsToAge()) {
            petUserCalc.setMonths(newMonths - appProperties.getDefaultPetMouthsToAge());
            petUserCalc.setAge(petUserCalc.getAge() + 1);
        } else {
            petUserCalc.setMonths(newMonths);
        }
        petUserCalc.setAge(petUserCalc.getAge() + valueAge);
        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    public PetUserRespAdminDto calculateIsDie(PetUserRespAdminDto petUserCalcDto) {
        log.debug("Calculating is die.");
        PetUser petUserCalc = petUserRespAdminDtoMapper.toEntity(petUserCalcDto);

        PetHabitat petHabitat = petHabitatRespAdminDtoMapper.toEntity(
                petHabitatServiceImpl.findPetHabitatById(petUserCalc.getPetHabitatId()));

        boolean isDomedCity = (petHabitat.getId() == 1 || petHabitat.getParentId() == 1);

        if (isDomedCity) {
            if (petUserCalc.getAge() >= appProperties.getDefaultPetAgeDieDomedCity()) {
                log.debug("PetUser death for AgeDieDomedCity: {}", petUserCalc.getAge());
                petUserSetDie(petUserCalc);
            }
        } else  {
          int probabilityAge = NumberUtils.random(appProperties.getDefaultPetAgeDieMinOutside(), appProperties.getDefaultPetAgeDieMaxOutside());
          if (petUserCalc.getAge() >= probabilityAge) {
              log.debug("PetUser death for Age: {}", petUserCalc.getAge());
              petUserSetDie(petUserCalc);
          }
        }
        return petUserRespAdminDtoMapper.toDto(petUserCalc);
    }

    private void petUserSetDie(PetUser petUserCalc) {
        petUserCalc.setDeathDate(LocalDateTime.now());
    }
}
