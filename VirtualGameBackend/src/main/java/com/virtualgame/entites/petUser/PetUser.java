package com.virtualgame.entites.petUser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "virtual_game_pets_users")
public class PetUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Pet's identifier in the system.", example = "1001")
    private Long id;
    @Column(unique = true)
    @Schema(description = "Pet's name.", example = "Esther")
    private String name;
    @Schema(description = "Proprietary user Pet's identifier in the system.", example = "1001")
    private Long userId;
    @Column(unique = true)
    @Schema(description = "Pet's type.", example = "1")
    private Long petTypeId;
    @Schema(description = "Pet's photo Url.", example = "https://i.pravatar.cc/200?img=46")
    private String imageUrl;
    @Schema(description = "Pet action's pets in the system.", example = "1")
    private Long petActionId;
    @Schema(description = "Pet's habitat in the system.", example = "1")
    private Long petHabitatId;
    @Schema(description = "Pet's happy.", example = "75")
    private Integer happy;
    @Schema(description = "Pet's happy.", example = "75")
    private Integer happyReps;
    @Schema(description = "Pet's tired.", example = "75")
    private Integer tired;
    @Schema(description = "Pet's tired.", example = "75")
    private Integer tiredReps;
    @Schema(description = "Pet's hungry.", example = "50")
    private Integer hungry;
    @Schema(description = "Pet's hungry.", example = "50")
    private Integer hungryReps;
    @Schema(description = "Pet's months.", example = "1")
    private Integer months;
    @Schema(description = "Pet's age.", example = "0")
    private Integer age;
    @Schema(description = "Date when the pet death.", example = "2025-07-15")
    private LocalDateTime deathDate;
    @Schema(description = "Reason for death petUser.", example = "PetUser death for Age")
    private String deathReason;
    @Schema(description = "Date when the user was created.", example = "2025-07-15")
    private LocalDateTime createdAt;
    @Schema(description = "User ID that created the user", example = "1001")
    private Long createdBy;
    @Schema(description = "Date when the user was updated.", example = "2025-07-15")
    private LocalDateTime updatedAt;
    @Schema(description = "User ID that updated the user.", example = "1001")
    private Long updatedBy;
    @Schema(description = "Date when the user was deleted with soft delete.", example = "2025-07-15")
    private LocalDateTime deletedAt;
    @Schema(description = "User ID that deleted the user.", example = "1001")
    private Long deletedBy;
}
