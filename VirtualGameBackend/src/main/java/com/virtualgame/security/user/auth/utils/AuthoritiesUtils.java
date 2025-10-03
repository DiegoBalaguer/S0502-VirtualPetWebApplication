package com.virtualgame.security.user.auth.utils;

import com.virtualgame.entites.userEntity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthoritiesUtils {

    public List<SimpleGrantedAuthority> getAuthorities(UserEntity userEntity) {

        List<SimpleGrantedAuthority> authorityList = getAuthoritiesRoles(userEntity);

        List<SimpleGrantedAuthority> permissionList = getAuthoritiesPermissions(userEntity);

        authorityList.addAll(permissionList);

     /*
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRolesEntity()
                .forEach(role ->
                        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEntityEnum().name()))));

        userEntity.getRolesEntity().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission ->
                        authorityList.add(new SimpleGrantedAuthority(permission.getName())));
    */
        return authorityList;
    }

    public List<SimpleGrantedAuthority> getAuthoritiesRoles(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorityRoleList = new ArrayList<>();

        userEntity.getRolesEntity()
                .forEach(role ->
                        authorityRoleList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEntityEnum().name()))));

        return authorityRoleList;
    }

    public List<SimpleGrantedAuthority> getAuthoritiesPermissions(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorityPermissionList = new ArrayList<>();

        userEntity.getRolesEntity().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission ->
                        authorityPermissionList.add(new SimpleGrantedAuthority(permission.getName())));

        return authorityPermissionList;
    }
}
