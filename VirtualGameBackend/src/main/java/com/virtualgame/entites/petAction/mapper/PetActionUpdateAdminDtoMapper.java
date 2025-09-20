package com.virtualgame.entites.petAction.mapper;

import com.virtualgame.entites.petAction.PetAction;
import com.virtualgame.entites.petAction.dto.PetActionUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetActionUpdateAdminDtoMapper {

    void forUpdateEntityFromDto(PetActionUpdateDto dto, @MappingTarget PetAction entity);

    PetAction toUpdateEntity(PetActionUpdateDto dto);

    PetActionUpdateDto toUpdateAdminDto(PetAction entity);
}

