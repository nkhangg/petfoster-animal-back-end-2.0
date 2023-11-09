package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    
}
