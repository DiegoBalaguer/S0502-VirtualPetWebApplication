package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.dto.UserRespUserDto;
import com.virtualgame.entites.userEntity.dto.UserRespAdminDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRespUserDtoMapper {

    UserEntity toEntity(UserRespUserDto dto);

    UserRespUserDto toBasicDto(UserEntity entity);

    UserRespUserDto toBasicFromFullDto(UserRespAdminDto dto);
}