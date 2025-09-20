package com.virtualgame.entites.petEntity.mapper;

import com.virtualgame.entites.petEntity.PetEntity;
import com.virtualgame.entites.petEntity.dto.PetCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetCreateDtoMapper {

    PetCreateDto toDto(PetEntity petEntity);

    PetEntity toEntity(PetCreateDto dto);

    void updateEntityFromDto(PetCreateDto dto, @MappingTarget PetEntity entity);
}