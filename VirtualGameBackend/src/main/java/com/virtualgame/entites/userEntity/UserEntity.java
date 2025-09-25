package com.virtualgame.entites.userEntity;

import com.virtualgame.security.user.roleEntity.RoleEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "virtual_game_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User's identifier in the system.", example = "1001")
    private Long id;
    @Column(unique = true)
    @Schema(description = "User's username.", example = "Manuel")
    private String username;
    @Column(unique = true)
    @Schema(description = "User's email.", example = "user@correo.me")
    private String email;
    @Schema(description = "User's language Id.", example = "1")
    private Long languageId;
    @Schema(description = "User's password.", example = "1234")
    private String password;
    @Schema(description = "User's photo Url.", example = "https://i.pravatar.cc/200?img=46")
    private String imageUrl;
    @Schema(description = "Is user's account is enabled in the system.", example = "1")
    private Boolean isEnabled;
    @Schema(description = "Is user's account is non-expired?", example = "1")
    private Boolean accountNoExpired;
    @Schema(description = "Is user's account is non-locked?", example = "1")
    private Boolean accountNoLocked;
    @Schema(description = "Is user's credential non-expired?", example = "1")
    private Boolean credentialNoExpired;
    @Schema(description = "Date when the user was created.", example = "2025-07-15")
    private LocalDateTime createdAt;
    @Schema(description = "Date when the user was updated.", example = "2025-07-15")
    private LocalDateTime updatedAt;
    @Schema(description = "User ID that updated the user.", example = "1001")
    private Long updatedBy;
    @Schema(description = "Date when the user was deleted with soft delete.", example = "2025-07-15")
    private LocalDateTime deletedAt;
    @Schema(description = "User ID that deleted the user.", example = "1001")
    private Long deletedBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "virtual_game_auth_users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> rolesEntity = new HashSet<>();
}