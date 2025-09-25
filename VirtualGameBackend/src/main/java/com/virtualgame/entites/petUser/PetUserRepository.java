package com.virtualgame.entites.petUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetUserRepository extends JpaRepository<PetUser, Long> {

    List<PetUser> findByNameContainingIgnoreCase(String name);

    List<PetUser> findByPetTypeId(Long petTypeId);

    @Query("SELECT p FROM PetUser p WHERE p.userId = :userId")
    List<PetUser> findByUserId(Long userId);

    @Query("SELECT p FROM PetUser p WHERE p.deletedAt IS NULL")
    List<PetUser> findAllActive();

    @Query("SELECT p FROM PetUser p WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<PetUser> findActiveById(@Param("id") Long id);

    @Query("DELETE FROM PetUser p WHERE p.userId = :id")
    Optional<PetUser> deleteByUserId(@Param("userId") Long userId);


}