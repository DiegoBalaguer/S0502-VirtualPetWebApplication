package com.virtualgame.entites.petAction;

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
@Table(name = "virtual_game_pets_actions")
public class PetAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Pet's action identifier in the system.", example = "1001")
    private Long id;
    @Column(unique = true)
    @Schema(description = "PetAction name.", example = "RUN")
    private String name;
    @Schema(description = "PetAction imageUrl.", example = "https://i.pravatar.cc/200?img=46")
    private String imageUrl;
    @Schema(description = "PetAction happy.", example = "5")
    private Integer happy;
    @Schema(description = "PetAction tired.", example = "-5")
    private Integer tired;
    @Schema(description = "PetAction hungry.", example = "10")
    private Integer hungry;
    @Schema(description = "PetAction months.", example = "1")
    private Integer months;
    @Schema(description = "PetAction life.", example = "0")
    private Integer age;
    @Schema(description = "PetAction min age for action.", example = "15")
    private Integer ageMin;
    @Schema(description = "petAction limited to this habitat.", example = "15")
    private Integer habitatId;
    @Schema(description = "Date when the petAction was created.", example = "2025-07-15")
    private LocalDateTime createdAt;
    @Schema(description = "User ID that created the petAction", example = "1001")
    private Long createdBy;
    @Schema(description = "Date when the petAction was updated.", example = "2025-07-15")
    private LocalDateTime updatedAt;
    @Schema(description = "User ID that updated the petAction.", example = "1001")
    private Long updatedBy;
    @Schema(description = "Date when the petAction was deleted with soft delete.", example = "2025-07-15")
    private LocalDateTime deletedAt;
    @Schema(description = "User ID that deleted the petAction.", example = "1001")
    private Long deletedBy;
}