package com.virtualgame.entites.petUser.mapper;

import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserCreateDtoMapper {

    PetUserCreateDto toDto(PetUser petUser);

    PetUser toEntity(PetUserCreateDto dto);

        void updateEntityFromDto(PetUserCreateDto dto, @MappingTarget PetUser entity);

    }
