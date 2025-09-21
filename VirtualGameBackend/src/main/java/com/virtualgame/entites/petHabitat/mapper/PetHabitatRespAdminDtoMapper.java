package com.virtualgame.entites.petHabitat.mapper;

import com.virtualgame.entites.petHabitat.PetHabitat;
import com.virtualgame.entites.petHabitat.dto.PetHabitatRespAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetHabitatRespAdminDtoMapper {

    PetHabitatRespAdminDto toDto(PetHabitat petHabitat);

    PetHabitat toEntity(PetHabitatRespAdminDto petHabitatRespAdminDto);

    void updateEntityFromDto(PetHabitatRespAdminDto dto, @MappingTarget PetHabitat entity);
}