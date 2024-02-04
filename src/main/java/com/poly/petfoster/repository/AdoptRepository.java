package com.poly.petfoster.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.Adopt;

public interface AdoptRepository extends JpaRepository<Adopt, Integer> {

    @Query("select a from Adopt a where a.status = 'Adopted'")
    public List<Adopt> getAdoptedPets();

    @Query(nativeQuery = true, value = "select * from adopt where pet_id = :petId")
    Adopt existsByPet(@Param("petId") String petId);

    @Query(nativeQuery = true, value = "select * from adopt where user_id = :userId and adopt_id = :adoptId")
    Adopt existsByUser(@Param("userId") String userId, @Param("adoptId") Integer adoptId);

    @Query(nativeQuery = true, value = "select * from adopt where pet_id = :petId and user_id = :userId and status like 'waiting'")
    Adopt existsByPetAndUser(@Param("petId") String petId, @Param("userId") String userId);

    @Query(nativeQuery = true, value = "select * from adopt where pet_id = :petId and status in ('Adopted', 'Registered')")
    Adopt exsitsAdopted(@Param("petId") String petId);

    @Query("SELECT a FROM Adopt a " + 
        "INNER JOIN a.user u " +
        "INNER JOIN a.pet p " +
        "WHERE (:name IS NULL OR u.displayName like %:name%) " +
        "AND (:petName IS NULL OR p.petName like %:petName%) " +
        "AND ((:registerStart IS NULL AND :registerEnd IS NULL) OR (convert(date, a.registerAt) BETWEEN :registerStart AND :registerEnd)) " +
        "AND ((:adoptStart IS NULL AND :adoptEnd IS NULL) OR (convert(date, a.adoptAt) BETWEEN :adoptStart AND :adoptEnd)) " +
        "AND (:status IS NULL OR a.status LIKE :status) " +
        "ORDER BY " +
        "CASE WHEN :sort = 'id-asc' THEN a.adoptId END ASC, " +
        "CASE WHEN :sort = 'id-desc' THEN a.adoptId END DESC, " +
        "CASE WHEN :sort = 'pet-asc' THEN p.petId END ASC, " +
        "CASE WHEN :sort = 'pet-desc' THEN p.petId END DESC, " +
        "CASE WHEN :sort = 'adopt-asc' THEN a.adoptAt END ASC, " +
        "CASE WHEN :sort = 'adopt-desc' THEN a.adoptAt END DESC "
        )
    public List<Adopt> filterAdopts(
        @Param("name") String name, 
        @Param("petName") String petName, 
        @Param("status") String status, 
        @Param("registerStart") Date registerStart, 
        @Param("registerEnd") Date registerEnd,
        @Param("adoptStart") Date adoptStart,
        @Param("adoptEnd") Date adoptEnd,
        @Param("sort") String sort
        );

}