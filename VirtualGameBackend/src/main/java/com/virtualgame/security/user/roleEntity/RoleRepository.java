package com.virtualgame.security.user.roleEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findRoleEntitiesByRoleEntityEnumIn(List<String> roleNames);

    // consulta para obtener los nombres de los roles
    @Query("SELECT r FROM RoleEntity r WHERE r.roleEntityEnum IN :roleNames")
    Set<RoleEntity> findByRoleEnumIn(@Param("roleNames") Set<String> roleNames);

}
