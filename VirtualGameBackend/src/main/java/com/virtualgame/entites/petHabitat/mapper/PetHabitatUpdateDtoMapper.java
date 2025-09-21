package com.virtualgame.entites.petHabitat.mapper;

import com.virtualgame.entites.petHabitat.PetHabitat;
import com.virtualgame.entites.petHabitat.dto.PetHabitatUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetHabitatUpdateDtoMapper {

    void forUpdateEntityFromDto(PetHabitatUpdateDto dto, @MappingTarget PetHabitat entity);

    PetHabitat toUpdateEntity(PetHabitatUpdateDto dto);

    PetHabitatUpdateDto toUpdateAdminDto(PetHabitat entity);
}

