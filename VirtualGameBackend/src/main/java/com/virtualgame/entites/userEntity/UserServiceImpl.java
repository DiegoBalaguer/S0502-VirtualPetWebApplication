package com.virtualgame.entites.userEntity;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.userEntity.mapper.UserRespAdminDtoMapper;
import com.virtualgame.entites.userEntity.mapper.UserListAdminDtoMapper;
import com.virtualgame.entites.userEntity.mapper.UserUpdateAdminDtoMapper;
import com.virtualgame.security.roleEntity.RoleEntity;
import com.virtualgame.security.roleEntity.RoleServiceImpl;
import com.virtualgame.security.auth.dto.AuthCreateUserRequestDto;
import com.virtualgame.security.auth.dto.AuthCreateUserRequestDtoMapper;
import com.virtualgame.entites.userEntity.dto.*;
import com.virtualgame.entites.petUser.PetUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleServiceImpl roleServiceImpl;
    private final PetUserServiceImpl petUserServiceImpl;
    private final AuthCreateUserRequestDtoMapper authCreateUserRequestDtoMapper;
    private final UserRespAdminDtoMapper userRespAdminDtoMapper;
    private final UserListAdminDtoMapper userListAdminDtoMapper;
    private final UserUpdateAdminDtoMapper userUpdateAdminDtoMapper;
    private static final String NAME_OBJECT = "user entity";

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
        userLoad.setPassword(passwordEncoder.encode(userLoad.getPassword()));
        if (userLoad.getImageUrl() == null || userLoad.getImageUrl().isEmpty()) {
            userLoad.setImageUrl(appProperties.getDefaultUserEntityImageUrl());
        }
        userLoad.setIsEnabled(true);
        userLoad.setAccountNoExpired(true);
        userLoad.setAccountNoLocked(true);
        userLoad.setCredentialNoExpired(true);
        userLoad.setCreatedAt(LocalDateTime.now());
        userLoad.setUpdatedAt(LocalDateTime.now());

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
        log.debug("Searching for {} by username: {}", NAME_OBJECT, username);
        return userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", username);
                    return new BadCredentialsException("Invalid credentials for username.");
                });
    }

    @Transactional(readOnly = true)
    public List<UserListAdminDto> findAllUserEntities() {
        log.debug("Finding all {}", NAME_OBJECT);

        List<UserEntity> userEntities = userRepository.findAll();
        log.info("Found {} {}", userEntities.size(), NAME_OBJECT);

        //return userEntityRepository.findAll().stream()
        return userEntities.stream()
                .map(userListAdminDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserRespAdminDto updateUserEntityById(Long userId, Long userIdAuth, UserRespAdminDto userRespAdminDto) {
        log.debug("Updating {} with ID: {}", NAME_OBJECT, userId);

        UserEntity userUpdate = findById(userId);
        userUpdateAdminDtoMapper.forUpdateEntityFromDto(userRespAdminDto, userUpdate);

        userUpdate.setUpdatedAt(LocalDateTime.now());
        userUpdate.setUpdatedBy(userIdAuth);

        UserEntity updateUserEntity = saveUserEntity(userUpdate);
        log.info("Updated successfully {} with ID: {}", NAME_OBJECT, userId);

        return userUpdateAdminDtoMapper.toUpdateFullDto(updateUserEntity);
    }

    @Transactional
    public void deleteSoftUserEntityById(Long userId, Long userIdAuth) {
        log.debug("Soft deleting {} with ID: {}", NAME_OBJECT, userId);

        UserEntity userDelete = findById(userId);

        petUserServiceImpl.softDeletePetUserByUserId(userId, userIdAuth);

        userDelete.setDeletedAt(LocalDateTime.now());
        userDelete.setDeletedBy(userIdAuth);

        saveUserEntity(userDelete);
        log.info("User Entity soft deleted successfully with ID: {}", userId);
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

        if (!passwordEncoder.matches(userUpdatePasswordDto.oldPassword(), userChange.getPassword())) {
            log.debug("Old password does not match with new password: {}", userUpdatePasswordDto.oldPassword());
            throw new BadCredentialsException("Invalid old password");
        }

        if (passwordEncoder.matches(userUpdatePasswordDto.newPassword(), userChange.getPassword())) {
            log.debug("New password does not match with new password: {}", userUpdatePasswordDto.newPassword());
            throw new IllegalArgumentException("New password must be different from the old password");
        }

        if (!userUpdatePasswordDto.newPassword().equals(userUpdatePasswordDto.newPasswordRetype())) {
            log.debug("New password does not match with new password: {}", userUpdatePasswordDto.newPassword());
            throw new IllegalArgumentException("New passwords do not match");
        }

        log.info("User password changed successfully with ID: {}", userIdChange);
        userChange.setPassword(passwordEncoder.encode(userUpdatePasswordDto.newPassword()));
        userChange.setUpdatedAt(LocalDateTime.now());
        userChange.setUpdatedBy(userIdAuth);
        saveUserEntity(userChange);
    }

    @Transactional(readOnly = true)
    public UserEntity findById(Long id) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Not found {} with ID: {}", NAME_OBJECT, id);
                    return new BadCredentialsException("Invalid credentials for username.");
                });
    }

    @Transactional
    public UserEntity saveUserEntity(UserEntity entitySave) {
        log.debug("Saving {}: {}", NAME_OBJECT, entitySave);
        return userRepository.save(entitySave);
    }
}
