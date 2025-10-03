package com.virtualgame.security.user.auth.mapper;

import com.virtualgame.security.user.auth.SecurityUser;
import com.virtualgame.security.user.auth.dto.AuthSecurityUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthSecurityUserDtoMapper {
    SecurityUser toEntity(AuthSecurityUserDto dto);
    AuthSecurityUserDto toDto(SecurityUser entity);

}
