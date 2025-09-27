package com.virtualgame.security.user.auth;

import com.virtualgame.entites.userEntity.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserServiceImpl userEntityService;

    public Long getCurrentUserId() {
        SecurityUser su = getSecurityUser();
        log.debug("###########################################################");
        log.debug("###########################################################");
        log.debug("#");
        log.debug("UserEntityService getCurrentUserId: {}", su);
        log.debug("UserEntityService getCurrentUserEmail: {}", getCurrentUserEmail());
        log.debug("UserEntityService getCurrentUserName: {}", getCurrentUserName());
        log.debug("#");
        log.debug("###########################################################");
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