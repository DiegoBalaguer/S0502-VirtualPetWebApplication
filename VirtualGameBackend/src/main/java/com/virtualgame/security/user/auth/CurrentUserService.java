package com.virtualgame.security.user.auth;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserServiceImpl userEntityService;

    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("username login: {}", username);
        return userEntityService.findUserEntityByUsername(username);
    }

    public Long getCurrentUserId() {
        UserEntity currentUser = getCurrentUser();
        log.debug("username login: {}", currentUser);
        return getCurrentUser().getId();
    }

}