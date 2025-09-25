package com.virtualgame.security.user.permissionEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "virtual_game_auth_permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Permissions identifier in the system.", example = "1001")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @Schema(description = "Names's permission in the system.", example = "read")
    private String name;
    // TODO: Falta por hacer el crud y la gestion de los permisos
}
