package com.virtualgame.entites.userEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
//    Optional<UserEntity> findUserEntityByEmail(String email);
/*
    @Query("SELECT COUNT(u) FROM UserEntity u JOIN u.roles r WHERE r.roleEnum = 'ADMIN' AND u.username != :currentUsername")
    long countUsersWithAdminRoleExcludingCurrent(@Param("currentUsername") String currentUsername);

*/

}
