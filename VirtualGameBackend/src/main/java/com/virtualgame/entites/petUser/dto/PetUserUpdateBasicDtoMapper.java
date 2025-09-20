package com.virtualgame.entites.petUser.dto;

import com.virtualgame.entites.petUser.PetUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserUpdateBasicDtoMapper {

    PetUserFullDto toDto(PetUser petUser);

    PetUser toEntity(PetUserFullDto petUserFullDto);

    void updateEntityFromDto(PetUserFullDto dto, @MappingTarget PetUser entity);
}