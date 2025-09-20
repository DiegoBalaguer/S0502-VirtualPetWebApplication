package com.virtualgame.entites.petAction.mapper;

import com.virtualgame.entites.petAction.PetAction;
import com.virtualgame.entites.petAction.dto.PetActionRespAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetActionRespAdminDtoMapper {

    PetActionRespAdminDto toDto(PetAction petAction);

    PetAction toEntity(PetActionRespAdminDto petActionRespAdminDto);

    void updateEntityFromDto(PetActionRespAdminDto dto, @MappingTarget PetAction entity);
}