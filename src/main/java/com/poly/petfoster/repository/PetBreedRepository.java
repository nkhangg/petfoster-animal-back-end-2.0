package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.PetBreed;

public interface PetBreedRepository extends JpaRepository<PetBreed, String> {

}
