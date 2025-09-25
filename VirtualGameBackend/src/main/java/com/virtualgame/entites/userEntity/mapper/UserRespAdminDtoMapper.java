package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.dto.UserRespAdminDto;
import com.virtualgame.entites.userEntity.dto.UserRespUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRespAdminDtoMapper {

    UserEntity toEntity(UserRespAdminDto dto);

    UserRespAdminDto toDto(UserEntity entity);

    UserRespAdminDto toDtoByAdminDto(UserRespUserDto respDto);
}

