package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.petfoster.entity.social.Posts;

public interface PostsRepository extends JpaRepository<Posts, Integer> {
    @Query(nativeQuery = true, value = "select top 4 * from posts")
    List<Posts> findAllByActive();
}
