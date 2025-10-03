package com.virtualgame.entites.userEntity;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.userEntity.mapper.UserRespAdminDtoMapper;
import com.virtualgame.entites.userEntity.mapper.UserListAdminDtoMapper;
import com.virtualgame.entites.userEntity.mapper.UserRespUserDtoMapper;
import com.virtualgame.entites.userEntity.mapper.UserUpdateAdminDtoMapper;
import com.virtualgame.entites.userEntity.userUtils.PasswordUtils;
import com.virtualgame.security.user.auth.dto.AuthSecurityUserDto;
import com.virtualgame.security.user.auth.utils.AuthoritiesUtils;
import com.virtualgame.security.user.roleEntity.RoleEntity;
import com.virtualgame.security.user.roleEntity.RoleServiceImpl;
import com.virtualgame.security.user.auth.dto.AuthCreateUserRequestDto;
import com.virtualgame.security.user.auth.mapper.AuthCreateUserRequestDtoMapper;
import com.virtualgame.entites.userEntity.dto.*;
import com.virtualgame.entites.petUser.PetUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final AppProperties appProperties;

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;
    private final RoleServiceImpl roleServiceImpl;
    private final PetUserServiceImpl petUserServiceImpl;
    private final AuthoritiesUtils authoritiesUtils;
    private final AuthCreateUserRequestDtoMapper authCreateUserRequestDtoMapper;
    private final UserRespAdminDtoMapper userRespAdminDtoMapper;
    private final UserListAdminDtoMapper userListAdminDtoMapper;
    private final UserUpdateAdminDtoMapper userUpdateAdminDtoMapper;
    private static final String NAME_OBJECT = "user entity";

    private final UserRespUserDtoMapper userRespUserDtoMapper;

    @Transactional
    public UserEntity createUserEntity(AuthCreateUserRequestDto authCreateUserRequestDto) {
        log.debug("Creating new {} with name: {}", NAME_OBJECT, authCreateUserRequestDto.username());
        List<String> roleRequest = authCreateUserRequestDto.roleRequest().roleListName();

        log.debug("Searching for roles: {}", roleRequest);
        Set<RoleEntity> roleEntitySet = roleServiceImpl.findRoleEntitiesByRoleEntityEnumIn(roleRequest);

        if (roleEntitySet.isEmpty()) {
            log.warn("Attempt to create user with non-existent roles: {}", roleRequest);
            throw new IllegalArgumentException("The role specified does not exist.");
        }

        UserEntity userLoad = authCreateUserRequestDtoMapper.toEntity(authCreateUserRequestDto);
        userLoad.setRolesEntity(roleEntitySet);
        userLoad.setPassword(passwordUtils.encode(userLoad.getPassword()));
        if (userLoad.getImageUrl() == null || userLoad.getImageUrl().isEmpty()) {
            userLoad.setImageUrl(appProperties.getDefaultUserEntityImageUrl());
        }
        userLoad.setIsEnabled(appProperties.getDefaultUserEnabled());
        userLoad.setAccountNoExpired(appProperties.getDefaultAccountNoExpired());
        userLoad.setAccountNoLocked(appProperties.getDefaultAccountNoLocked());
        userLoad.setCredentialNoExpired(appProperties.getDefaultCredentialNoExpired());
        userLoad.setCreatedAt(LocalDateTime.now());

        UserEntity savedEntity = saveUserEntity(userLoad);
        log.info("Created {} successfully with ID: {}", NAME_OBJECT, savedEntity.getId());

        return savedEntity;
    }

    @Transactional(readOnly = true)
    public UserRespAdminDto findUserEntityById(Long userId) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, userId);

        UserEntity findEntity = findById(userId);

        log.debug("Found {}: {}", NAME_OBJECT, findEntity.getUsername());
        return userRespAdminDtoMapper.toDto(findEntity);
    }

    @Transactional(readOnly = true)
    public UserEntity findUserEntityByUsername(String username) {
        log.debug("Searching for {} by email: {}", NAME_OBJECT, username);
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", username);
                    return new BadCredentialsException("Invalid credentials for email.");
                });
    }

    @Transactional(readOnly = true)
    public UserEntity findUserEntityByEmail(String email) {
        log.debug("Searching for {} by email: {}", NAME_OBJECT, email);
        return userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> {
                log.warn("{} not found with email: {}", NAME_OBJECT, email);
                    return new BadCredentialsException("Invalid credentials for user email.");
                });
    }

    @Transactional(readOnly = true)
    public List<UserListAdminDto> findAllUserEntities() {
        log.debug("Finding all {}", NAME_OBJECT);

        List<UserEntity> userEntities = userRepository.findAll();
        log.info("Found {} {}", userEntities.size(), NAME_OBJECT);

        return userEntities.stream()
                .map(userListAdminDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserUpdateAdminDto updateUserEntityById(Long userId, Long userIdAuth, UserUpdateAdminDto userUpdateAdminDto) {
        log.debug("Updating {} with ID: {}", NAME_OBJECT, userId);

        UserEntity userUpdate = findById(userId);
        userUpdateAdminDtoMapper.forUpdateEntityFromDto(userUpdateAdminDto, userUpdate);

        userUpdate.setUpdatedBy(userIdAuth);

        UserEntity updateUserEntity = saveUserEntity(userUpdate);
        log.info("Updated successfully {} with ID: {}", NAME_OBJECT, userId);

        return userUpdateAdminDtoMapper.toDto(updateUserEntity);
    }

    @Transactional
    public void deleteSoftUserEntityById(AuthSecurityUserDto securityUserDto, Long deleteUserId) {
        log.debug("Soft deleting {} with ID: {}", NAME_OBJECT, deleteUserId);

        // TODO: no es correcto, cambiar por un update a todos los que son del deleteUserId
        UserEntity userDelete = findById(deleteUserId);

        petUserServiceImpl.softDeletePetUserByUserId(securityUserDto, deleteUserId);

        userDelete.setDeletedAt(LocalDateTime.now());
        userDelete.setDeletedBy(securityUserDto.userId());

        saveUserEntity(userDelete);
        log.info("User Entity soft deleted successfully with ID: {}", deleteUserId);
    }

    @Transactional
    public void deleteUserEntityById(Long userId) {
        log.debug("Hard deleting {} with ID: {}", NAME_OBJECT, userId);

        petUserServiceImpl.hardDeletePetUserByUserId(userId);

        UserEntity userDelete = findById(userId);
        userRepository.deleteById(userDelete.getId());
        log.info("Hard deleted successfully {} with ID: {}", NAME_OBJECT, userId);
    }

    @Transactional
    public void updatePassword(Long userIdChange, Long userIdAuth, UserUpdatePasswordDto userUpdatePasswordDto) {
        log.debug("Updating password for {} with id: {}", NAME_OBJECT, userIdChange);
        UserEntity userChange = findById(userIdChange);

        userChange.setPassword(passwordUtils.changePassword(userUpdatePasswordDto, userChange.getPassword()));
        userChange.setUpdatedBy(userIdAuth);

        log.info("User password changed successfully with ID: {}", userIdChange);

        saveUserEntity(userChange);
    }

    @Transactional(readOnly = true)
    public UserEntity findById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Not found {} with ID: {}", NAME_OBJECT, id);
                    return new BadCredentialsException("Invalid credentials for email.");
                });
    }

    @Transactional
    public UserEntity saveUserEntity(UserEntity entitySave) {
        log.debug("Saving {}: {}", NAME_OBJECT, entitySave);
        entitySave.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(entitySave);
    }

    public List<SimpleGrantedAuthority> testList(Long id) {
        UserEntity userEntity = findById(id);
        return authoritiesUtils.getAuthoritiesRoles(userEntity);

    }
    public String test(Long id) {
        UserEntity userEntity = findById(id);
        return authoritiesUtils.getAuthoritiesRoles(userEntity).toString();

    }

    public UserRespUserDto testDto(Long id) {
        UserEntity userEntity = findById(id);
        return userRespUserDtoMapper.toDto(userEntity);
    }

}
