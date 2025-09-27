package com.virtualgame.security.user.auth;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.userEntity.UserServiceImpl;
import com.virtualgame.security.user.auth.dto.AuthCreateRoleRequestDto;
import com.virtualgame.security.user.auth.dto.AuthCreateUserRequestDto;
import com.virtualgame.security.user.auth.dto.AuthLoginRequestDto;
import com.virtualgame.security.user.auth.dto.AuthResponseDto;
import com.virtualgame.security.user.JwtUtils;
import com.virtualgame.entites.userEntity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;
    private final JwtUtils jwtUtils;


    public AuthResponseDto loginUser(AuthLoginRequestDto req) {
        log.info("login attempt with email: {}", req.email());
        String email = req.email();
        String password = req.password();

        Authentication authentication = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity userEntity = userServiceImpl.findUserEntityByEmail(email);

        String accessToken = jwtUtils.createToken(authentication, userEntity.getId(), userEntity.getUsername());

        return new AuthResponseDto(email, "User logged successfully", accessToken, true);
    }


    public AuthResponseDto registerUser(AuthCreateUserRequestDto req) {
        log.info("Starting user register for: {}", req.email());

        AuthCreateUserRequestDto savedDto =
                new AuthCreateUserRequestDto(req.username(), req.email(), req.password(), req.photoUrl(), new AuthCreateRoleRequestDto(List.of(appProperties.getDefaultRole())));

        UserEntity userCreated = userServiceImpl.createUserEntity(savedDto);

        log.debug("User created successfully with ID: {}", userCreated.getId());

        return loginUser(new AuthLoginRequestDto(req.email(), req.password()));

    }

    public Authentication authenticate(String email, String password) {

        CustomUserDetails userDetails = loadUserByUsername(email);

        if (userDetails == null) {
            log.warn("Authentication failed - User not found: {}", email);
            throw new BadCredentialsException("Invalid email or password.");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.warn("Authentication failed - Invalid password for user: {}", email);
            throw new BadCredentialsException("Invalid email or password.");
        }
        log.info("Authentication successful for user: {}", email);

        SecurityUser securityUser = new SecurityUser(userDetails.getUserId(), userDetails.getUsername(), userDetails.getName());

        return new UsernamePasswordAuthenticationToken(securityUser, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws BadCredentialsException {

        log.info("UserEntityService loadUserByUsername email: {}", email);

        UserEntity userEntity = userServiceImpl.findUserEntityByEmail(email);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRolesEntity()
                .forEach(role ->
                        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEntityEnum().name()))));

        userEntity.getRolesEntity().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission ->
                        authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        log.info("User loaded successfully: {}", email);

        return new CustomUserDetails(userEntity, authorityList);
    }
}
