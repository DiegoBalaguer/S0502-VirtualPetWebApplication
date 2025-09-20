package com.virtualgame.security.auth;

import com.virtualgame.entites.userEntity.UserServiceImpl;
import com.virtualgame.security.auth.dto.AuthCreateUserRequestDto;
import com.virtualgame.security.auth.dto.AuthLoginRequestDto;
import com.virtualgame.security.auth.dto.AuthResponseDto;
import com.virtualgame.security.JwtUtils;
import com.virtualgame.entites.userEntity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {

        log.info("UserEntityService loadUserByUsername username={}", username);

        UserEntity userEntity = userServiceImpl.findUserEntityByUsername(username);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRolesEntity()
                .forEach(role ->
                        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEntityEnum().name()))));

        userEntity.getRolesEntity().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission ->
                        authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        log.info("User loaded successfully: {}", username);

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getIsEnabled(),
                userEntity.getAccountNoExpired(),
                userEntity.getCredentialNoExpired(),
                userEntity.getAccountNoLocked(),
                authorityList);
    }

    public AuthResponseDto loginUser(AuthLoginRequestDto authLoginRequestDto) {
        String username = authLoginRequestDto.username();
        String password = authLoginRequestDto.password();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        return new AuthResponseDto(username, "User logged successfully", accessToken, true);
    }

    public Authentication authenticate(String username, String password) {

        UserDetails userDetails = loadUserByUsername(username);

        if (userDetails == null) {
            log.warn("Authentication failed - User not found: {}", username);
            throw new BadCredentialsException("Invalid username or password.");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            log.warn("Authentication failed - Invalid password for user: {}", username);
            throw new BadCredentialsException("Invalid username or password.");
        }
        log.info("Authentication successful for user: {}", username);
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponseDto createUser(AuthCreateUserRequestDto authCreateUserRequestDto) {

        log.info("Starting user creation for: {}", authCreateUserRequestDto.username());


        //UserEntity userCreated = userEntityServiceImpl.createUserEntity(authCreateUserRequestDto);
        UserEntity userCreated = userServiceImpl.createUserEntity(authCreateUserRequestDto);

        log.debug("User created successfully with ID: {}", userCreated.getId());

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userCreated.getRolesEntity()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEntityEnum().name()))));

        userCreated.getRolesEntity()
                .stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        log.debug("Assigned authorities: {}", authorityList.stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorityList);

        String accessToken = jwtUtils.createToken(authentication);
        log.debug("Token generated successfully");

        AuthResponseDto authResponseDto = new AuthResponseDto(userCreated.getUsername(), "User created successfully", accessToken, true);

        log.info("User created successfully: {}", userCreated.getUsername());
        return authResponseDto;
    }
}
