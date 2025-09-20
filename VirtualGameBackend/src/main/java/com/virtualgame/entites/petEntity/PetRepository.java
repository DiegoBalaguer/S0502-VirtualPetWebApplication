package com.virtualgame.entites.petEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface PetRepository extends JpaRepository<PetEntity, Long> {

    List<PetEntity> findByNameContainingIgnoreCase(String name);
    }

