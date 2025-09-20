package com.virtualgame.entites.petAction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetActionRepository extends JpaRepository<PetAction, Long> {
    Boolean existsByName(String name);
}
