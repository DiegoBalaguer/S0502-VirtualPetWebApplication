package com.virtualgame.security.user.auth;

import com.virtualgame.security.user.auth.dto.AuthSecurityUserDto;
import com.virtualgame.security.user.auth.mapper.AuthSecurityUserDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final AuthSecurityUserDtoMapper authSecurityUserDtoMapper;

    public Long getCurrentUserId() {
        SecurityUser su = getSecurityUser();
         log.debug("UserEntityService getCurrentUserId: {}", su);
        return su != null ? su.getUserId() : null;
    }

    public String getCurrentUserEmail() {
        SecurityUser su = getSecurityUser();
        return su != null ? su.getEmail() : null;
    }

    public String getCurrentUserName() {
        SecurityUser su = getSecurityUser();
        return su != null ? su.getName() : null;
    }

    public String getCurrentUserLanguageCode() {
        SecurityUser su = getSecurityUser();
        return su != null ? su.getLanguageCode() : null;
    }

    public AuthSecurityUserDto getCurrentUserDto() {
        return authSecurityUserDtoMapper.toDto(getSecurityUser());
    }

    private SecurityUser getSecurityUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof SecurityUser su) {
            return su;
        } else {
            log.debug("SecurityUser not found in SecurityContext");
            return null;
        }
    }
}