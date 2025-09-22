package com.virtualgame.entites.petUser.mapper;

import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRuleStatusDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetUserRuleStatusDtoMapper {
    PetUserRuleStatusDto toDtoFromActionDto(PetActionRespAdminDto actionDto);

    PetUserRuleStatusDto toDtoFromHabitatDto(PetHabitatRespAdminDto habitatDto);
}