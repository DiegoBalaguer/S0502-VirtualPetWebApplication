package com.virtualgame.entites.petHabitat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetHabitatRepository extends JpaRepository<PetHabitat, Long> {

}

