package com.virtualgame.entites.petImage;

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
@Table(name = "virtual_game_pets_images")
public class PetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "PetImage identifier in the system.", example = "1001")
    private Long id;
    @Schema(description = "PetTypeId of petUser.", example = "1001")
    private Long petId;
    @Schema(description = "PetImage age.", example = "0")
    private Integer age;
    @Schema(description = "PetImage imageUrl.", example = "https://i.pravatar.cc/200?img=46")
    private String imageUrl;
    @Schema(description = "Date when the PetImage was created.", example = "2025-07-15")
    private LocalDateTime createdAt;
    @Schema(description = "User ID that created the PetImage", example = "1001")
    private Long createdBy;
    @Schema(description = "Date when the PetImage was updated.", example = "2025-07-15")
    private LocalDateTime updatedAt;
    @Schema(description = "User ID that updated the PetImage.", example = "1001")
    private Long updatedBy;
    @Schema(description = "Date when the PetImage was deleted with soft delete.", example = "2025-07-15")
    private LocalDateTime deletedAt;
    @Schema(description = "User ID that deleted the PetImage.", example = "1001")
    private Long deletedBy;
}