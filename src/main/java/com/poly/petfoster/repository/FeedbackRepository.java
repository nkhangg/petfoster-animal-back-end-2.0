package com.poly.petfoster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.petfoster.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
