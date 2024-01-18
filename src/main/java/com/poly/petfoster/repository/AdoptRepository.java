package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.petfoster.entity.Adopt;

public interface AdoptRepository extends JpaRepository<Adopt, Integer> {

    @Query("select a from Adopt a where a.status = 'Adopted'")
    public List<Adopt> getAdoptedPets();

}