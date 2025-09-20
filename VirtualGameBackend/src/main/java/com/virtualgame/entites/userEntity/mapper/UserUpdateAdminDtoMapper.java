package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.dto.UserRespAdminDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateAdminDtoMapper {

    void forUpdateEntityFromDto(UserRespAdminDto dto, @MappingTarget UserEntity entity);

    UserEntity toUpdateEntity(UserRespAdminDto dto);

    UserRespAdminDto toUpdateFullDto(UserEntity entity);
}

