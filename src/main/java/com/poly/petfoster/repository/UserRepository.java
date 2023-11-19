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

        @Query(nativeQuery = true, value = "SELECT * FROM Users u WHERE u.is_active = 1 " +
                        "AND (username IS NULL OR u.username LIKE %:username% ) " +
                        "AND (fullname IS NULL OR u.fullname LIKE %:fullname% ) " +
                        "AND (email IS NULL OR u.email LIKE %:email%) ")
        public List<User> findAll(@Param("username") String username,
                        @Param("fullname") String fullname,
                        @Param("email") String email);

        @Query("select u from Users u where u.email = :email")
        public Optional<User> findByEmail(@Param("email") String email);

        @Query("select u from Users u where u.username = :username")
        public Optional<User> findByUsername(@Param("username") String username);

        @Query(nativeQuery = true, value = "select count(*) from users where is_active = 'true'")
        public Integer getTotalUsers();

        @Query(nativeQuery = true, value = "select * from users where token = :token")
        public User findByToken(@Param("token") String token);
}
