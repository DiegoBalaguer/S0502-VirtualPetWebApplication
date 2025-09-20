package com.virtualgame.entites.petEntity.mapper;

import com.virtualgame.entites.petEntity.PetEntity;
import com.virtualgame.entites.petEntity.dto.PetFullDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetFullDtoMapper {

    PetFullDto toDto(PetEntity petEntity);

    PetEntity toEntity(PetFullDto petFullDto);

    void updateEntityFromDto(PetFullDto dto, @MappingTarget PetEntity entity);
}