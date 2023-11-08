package com.poly.petfoster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    @Query("select p from Users p where p.isActive = true")
    public List<User> findAll();

    @Query("select u from Users u where u.email = :email")
    public Optional<User> findByEmail(@Param("email") String email);

    @Query("select u from Users u where u.username = :username")
    public Optional<User> findByUsername(@Param("username") String username);

    @Query(nativeQuery = true, value = "select count(*) from users where is_active = 'true'")
    public Integer getTotalUsers();

    @Query(nativeQuery = true, value = "select * from users where token = :token")
    public User findByToken(@Param("token") String token);

}
