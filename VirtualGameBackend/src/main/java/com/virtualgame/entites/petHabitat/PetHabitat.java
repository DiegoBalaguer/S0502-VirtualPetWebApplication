package com.virtualgame.entites.petHabitat;

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
@Table(name = "virtual_game_pets_habitats")
public class PetHabitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Pet's habitat identifier in the system.", example = "1001")
    private Long id;
    @Schema(description = "Pet's habitat parent identifier.", example = "1001")
    private Long parentId;
    @Column(unique = true)
    @Schema(description = "Pet's habitat name.", example = "RUN")
    private String name;
    @Schema(description = "Pet's habitat imageUrl.", example = "https://i.pravatar.cc/200?img=46")
    private String imageUrl;
    @Schema(description = "Pet's habitat happy.", example = "5")
    private Integer happy;
    @Schema(description = "Pet's habitat tired.", example = "-5")
    private Integer tired;
    @Schema(description = "Pet's habitat hungry.", example = "10")
    private Integer hungry;
    @Schema(description = "Pet's habitat months.", example = "1")
    private Integer months;
    @Schema(description = "Pet's habitat age.", example = "0")
    private Integer age;
    @Schema(description = "Minium age for pet's habitat age.", example = "0")
    private Integer ageMin;
    @Schema(description = "Date when the pet habitat was created.", example = "2025-07-15")
    private LocalDateTime createdAt;
    @Schema(description = "User ID that created the habitat", example = "1001")
    private Long createdBy;
    @Schema(description = "Date when the pet habitat was updated.", example = "2025-07-15")
    private LocalDateTime updatedAt;
    @Schema(description = "User ID that updated the pet habitat.", example = "1001")
    private Long updatedBy;
    @Schema(description = "Date when the pet habitat was deleted with soft delete.", example = "2025-07-15")
    private LocalDateTime deletedAt;
    @Schema(description = "User ID that deleted the pet habitat.", example = "1001")
    private Long deletedBy;
}