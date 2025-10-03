package com.virtualgame.entites.petImage;

import com.virtualgame.entites.petAction.PetAction;
import com.virtualgame.entites.petImage.dto.PetImageRespAdminDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetImageRepository extends JpaRepository<PetImage, Long> {

    //PetImageRespAdminDto findByPetIdAndAge(Long petId, Integer age);

    @Query("SELECT pa FROM PetImage pa WHERE pa.petId = :petId AND pa.age = :age")
    PetImageRespAdminDto findByPetIdAndAge(@Param("petId") Long petId, @Param("age") Integer age);
}

