package com.virtualgame.translation.languageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Long> {
    Optional<LanguageEntity> findByCode(String code);
    boolean existsByCode(String code);
}