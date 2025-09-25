package com.virtualgame.entites.petAction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetActionRepository extends JpaRepository<PetAction, Long> {
    Boolean existsByName(String name);

    @Query("SELECT pa FROM PetAction pa WHERE pa.habitatId = :habitatId OR pa.habitatId IS NULL")
    List<PetAction> findByHabitatIdOrHabitatIsNull(@Param("habitatId") long habitatId);


}
