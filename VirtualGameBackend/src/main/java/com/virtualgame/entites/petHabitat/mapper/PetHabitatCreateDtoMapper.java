package com.virtualgame.entites.petHabitat.mapper;

import com.virtualgame.entites.petHabitat.PetHabitat;
import com.virtualgame.entites.petHabitat.dto.PetHabitatCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetHabitatCreateDtoMapper {

    PetHabitatCreateDto toDto(PetHabitat petHabitat);

    PetHabitat toEntity(PetHabitatCreateDto dto);

    void updateEntityFromDto(PetHabitatCreateDto dto, @MappingTarget PetHabitat entity);
}