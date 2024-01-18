package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.petfoster.entity.Donate;

public interface DonateRepository extends JpaRepository<Donate, Integer> {
    
    @Query("select sum(d.donateAmount) from Donate d")
    public Integer getDonation();

}
