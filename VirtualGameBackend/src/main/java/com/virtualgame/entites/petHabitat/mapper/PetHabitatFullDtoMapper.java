package com.virtualgame.entites.petHabitat.mapper;

import com.virtualgame.entites.petHabitat.PetHabitat;
import com.virtualgame.entites.petHabitat.dto.PetHabitatFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetHabitatFullDtoMapper {

    PetHabitatFullDto toDto(PetHabitat petHabitat);

    PetHabitat toEntity(PetHabitatFullDto petHabitatFullDto);

    void updateEntityFromDto(PetHabitatFullDto dto, @MappingTarget PetHabitat entity);
}