package com.virtualgame.entites.petAction.mapper;

import com.virtualgame.entites.petAction.PetAction;
import com.virtualgame.entites.petAction.dto.PetActionCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetActionCreateDtoMapper {

    PetActionCreateDto toDto(PetAction petAction);

    PetAction toEntity(PetActionCreateDto dto);

    void updateEntityFromDto(PetActionCreateDto dto, @MappingTarget PetAction entity);
}