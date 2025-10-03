package com.virtualgame.entites.userEntity.userUtils;

import com.virtualgame.entites.userEntity.dto.UserUpdatePasswordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    public String changePassword(UserUpdatePasswordDto userUpdatePasswordDto, String oldPassword) {
        if (!passwordEncoder.matches(userUpdatePasswordDto.oldPassword(), oldPassword)) {
            log.debug("Old password does not match with new password: {}", userUpdatePasswordDto.oldPassword());
            throw new BadCredentialsException("Invalid old password");
        }

        if (passwordEncoder.matches(userUpdatePasswordDto.newPassword(), oldPassword)) {
            log.debug("New password does not match with password: {}", userUpdatePasswordDto.newPassword());
            throw new IllegalArgumentException("New password must be different from the old password");
        }

        if (!userUpdatePasswordDto.newPassword().equals(userUpdatePasswordDto.newPasswordRetype())) {
            log.debug("New password does not match with new password: {}", userUpdatePasswordDto.newPassword());
            throw new IllegalArgumentException("New passwords do not match");
        }
        return encode(userUpdatePasswordDto.newPassword());
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}