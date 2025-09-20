package com.virtualgame.security.auth;

import com.virtualgame.entites.userEntity.UserEntity;
import com.virtualgame.entites.userEntity.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserServiceImpl userEntityService;

    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userEntityService.findUserEntityByUsername(username);
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

}