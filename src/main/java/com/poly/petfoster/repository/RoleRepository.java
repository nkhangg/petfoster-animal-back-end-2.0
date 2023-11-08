package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.petfoster.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    @Query(nativeQuery = true, value = "select * from role where id = 4")
    public Role getRoleUser();

}
