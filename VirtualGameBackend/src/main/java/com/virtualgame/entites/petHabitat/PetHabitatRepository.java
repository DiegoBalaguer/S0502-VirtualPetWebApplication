package com.virtualgame.entites.petHabitat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetHabitatRepository extends JpaRepository<PetHabitat, Long> {
    List<PetHabitat> findAllByParentId(Long parentId);

}

