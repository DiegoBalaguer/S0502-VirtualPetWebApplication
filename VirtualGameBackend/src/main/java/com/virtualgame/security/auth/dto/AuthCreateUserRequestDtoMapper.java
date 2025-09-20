package com.virtualgame.security.auth.dto;

import com.virtualgame.entites.userEntity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthCreateUserRequestDtoMapper {
    UserEntity toEntity(AuthCreateUserRequestDto dto);
    AuthCreateUserRequestDto toDto(UserEntity entity);

}
