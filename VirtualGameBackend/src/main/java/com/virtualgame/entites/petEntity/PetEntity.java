package com.virtualgame.entites.petEntity;

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
@Table(name = "virtual_game_pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Pet's type identifier in the system.", example = "1001")
    private Long id;
    @Column(unique = true)
    @Schema(description = "Pet's type name.", example = "Gatitos")
    private String name;
    @Schema(description = "Pet's type imageUrl.", example = "https://i.pravatar.cc/200?img=46")
    private String imageUrl;
    @Schema(description = "Date when the pet type was created.", example = "2025-07-15")
    private LocalDateTime createdAt;
    @Schema(description = "User ID that created the pet", example = "1001")
    private Long createdBy;
    @Schema(description = "Date when the pet type was updated.", example = "2025-07-15")
    private LocalDateTime updatedAt;
    @Schema(description = "User ID that updated the pet type.", example = "1001")
    private Long updatedBy;
    @Schema(description = "Date when the pet type was deleted with soft delete.", example = "2025-07-15")
    private LocalDateTime deletedAt;
    @Schema(description = "User ID that deleted the pet type.", example = "1001")
    private Long deletedBy;
}
