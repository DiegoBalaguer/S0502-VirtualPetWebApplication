package com.virtualgame.entites.petImage;

import com.virtualgame.config.properties.AppProperties;
import com.virtualgame.entites.petImage.dto.PetImageRequestDto;
import com.virtualgame.entites.petImage.dto.PetImageRespAdminDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetImageServiceImpl {

    private final PetImageRepository petImageRepository;
    private static final String NAME_OBJECT = "pet image";
    private final AppProperties appProperties;


    @Transactional(readOnly = true)
    public PetImageRespAdminDto findPetImageUrlForAgeByIdAndAge(PetImageRequestDto dto) {
        log.debug("Finding {} by ID: {}", NAME_OBJECT, dto.petId());

       PetImageRespAdminDto adminDto = petImageRepository.findByPetIdAndAge(dto.petId(), calculateAge(dto.age()));

        log.debug("Found {}: {}", NAME_OBJECT, adminDto.imageUrl());
        return adminDto;
    }

    private Integer calculateAge(Integer age) {

        if (age == null || age < 16 ) {
            return 15;
        } else if (age < 31) {
            return 30;
        } else if (age < 65) {
            return 65;
        }
        return 99;
    }
}
