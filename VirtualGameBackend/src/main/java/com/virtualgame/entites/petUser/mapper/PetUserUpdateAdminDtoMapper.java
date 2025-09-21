package com.virtualgame.entites.petUser.mapper;

import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserUpdateAdminDtoMapper {

    PetUserUpdateDto toDto(PetUser entity);

    PetUser toEntity(PetUserUpdateDto dto);

    void updateEntityFromDto(PetUserUpdateDto dto, @MappingTarget PetUser entity);
}