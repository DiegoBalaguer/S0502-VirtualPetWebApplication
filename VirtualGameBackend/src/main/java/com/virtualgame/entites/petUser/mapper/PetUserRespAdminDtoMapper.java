package com.virtualgame.entites.petUser.mapper;

import com.virtualgame.entites.petUser.PetUser;
import com.virtualgame.entites.petUser.dto.PetUserRespAdminDto;
import com.virtualgame.entites.petUser.dto.PetUserRespTaskAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetUserRespAdminDtoMapper {

    PetUserRespAdminDto toDto(PetUser petUser);

    PetUser toEntity(PetUserRespAdminDto petUserRespAdminDto);

    PetUserRespAdminDto toDtoByTaskDto(PetUserRespTaskAdminDto taskAdminDto);

    void updateEntityFromDto(PetUserRespAdminDto dto, @MappingTarget PetUser entity);
}