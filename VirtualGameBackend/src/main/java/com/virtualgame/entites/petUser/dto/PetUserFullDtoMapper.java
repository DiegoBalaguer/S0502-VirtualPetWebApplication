package com.virtualgame.entites.petUser.dto;

import com.virtualgame.entites.petUser.PetUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserFullDtoMapper {

    PetUserFullDto toFullDto(PetUser petUser);

    PetUser toEntity(PetUserFullDto petUserFullDto);

    void forUpdateEntityFromDto(PetUserFullDto dto, @MappingTarget PetUser entity);
}