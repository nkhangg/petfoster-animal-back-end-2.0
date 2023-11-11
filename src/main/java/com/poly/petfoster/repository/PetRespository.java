package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.poly.petfoster.entity.Pet;

public interface PetRespository extends JpaRepository<Pet, String> {

    @Query(nativeQuery = true, value = "select top 8 * from pet order by create_at desc;")
    public List<Pet> selectRecentPet();

}
