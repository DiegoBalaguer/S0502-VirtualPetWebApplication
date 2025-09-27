package com.virtualgame.security.user.roleEntity;


import com.virtualgame.security.user.permissionEntity.PermissionEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "virtual_game_auth_roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Rol's identifier in the system.", example = "1001")
    private Long id;

    @Column(name = "role_name")
    @Schema(description = "Role's email in the system.", example = "ADMIN")
    @Enumerated(EnumType.STRING)
    private RoleEntityEnum roleEntityEnum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "virtual_game_auth_roles_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> permissionList = new HashSet<>();
}
