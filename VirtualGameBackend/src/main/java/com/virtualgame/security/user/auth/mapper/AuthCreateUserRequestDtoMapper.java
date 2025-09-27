package com.virtualgame.security.user.auth.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.security.user.auth.dto.AuthCreateUserRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthCreateUserRequestDtoMapper {
    UserEntity toEntity(AuthCreateUserRequestDto dto);
    AuthCreateUserRequestDto toDto(UserEntity entity);

}
