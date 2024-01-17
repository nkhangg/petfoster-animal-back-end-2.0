package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, String> {

    @Query(nativeQuery = true, value = "select top 8 * from pet")
    List<Pet> findAllByActive();

    @Query(nativeQuery = true, value = "select top 8 * from pet where (breed_id = :id or age = :size) and pet_id != :petId")
    List<Pet> findByPetStyleAndIgnorePetId(@Param("id") String id, @Param("size") String size,
            @Param("petId") String petId);
}
