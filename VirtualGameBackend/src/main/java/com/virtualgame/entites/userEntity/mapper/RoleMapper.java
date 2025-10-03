package com.virtualgame.entites.userEntity.mapper;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.security.user.auth.utils.AuthoritiesUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final AuthoritiesUtils authoritiesUtils;

    @Named("mapRoles")
    public String mapRoles(UserEntity userEntity) {
        return authoritiesUtils.getAuthoritiesRoles(userEntity).toString();
    }
}