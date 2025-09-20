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
    @Schema(description = "Pet's action name.", example = "RUN")
    private String name;
    @Schema(description = "Pet's action imageUrl.", example = "https://i.pravatar.cc/200?img=46")
    private String imageUrl;
    @Schema(description = "Pet's action happy.", example = "5")
    private Integer happy;
    @Schema(description = "Pet's action tired.", example = "-5")
    private Integer tired;
    @Schema(description = "Pet's action hungry.", example = "10")
    private Integer hungry;
    @Schema(description = "Pet's action months.", example = "1")
    private Integer months;
    @Schema(description = "Pet's action life.", example = "0")
    private Integer age;
    @Schema(description = "Pet's action max happy.", example = "125")
    private Integer happyMax;
    @Schema(description = "Pet's action min age for action.", example = "15")
    private Integer ageMin;
    @Schema(description = "Date when the pet action was created.", example = "2025-07-15")
    private LocalDateTime createdAt;
    @Schema(description = "User ID that created the pet action", example = "1001")
    private Long createdBy;
    @Schema(description = "Date when the pet action was updated.", example = "2025-07-15")
    private LocalDateTime updatedAt;
    @Schema(description = "User ID that updated the pet action.", example = "1001")
    private Long updatedBy;
    @Schema(description = "Date when the pet action was deleted with soft delete.", example = "2025-07-15")
    private LocalDateTime deletedAt;
    @Schema(description = "User ID that deleted the pet action.", example = "1001")
    private Long deletedBy;
}