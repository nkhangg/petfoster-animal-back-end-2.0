package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Brand;
import com.poly.petfoster.entity.Review;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query( nativeQuery=true, value="select* from brand where brand= :name")
    public Optional<List<Brand>> findbyName(@Param("name") String name);

}
