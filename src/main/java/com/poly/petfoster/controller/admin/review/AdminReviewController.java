package com.poly.petfoster.controller.admin.review;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.review.AdminReviewService;

@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

    @Autowired
    AdminReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> filterReviews(
            @RequestParam("productName") Optional<String> productName,
            @RequestParam("minStar") Optional<Integer> minStar,
            @RequestParam("maxStar") Optional<Integer> maxStar,
            @RequestParam("sort") Optional<String> sort) {
        return ResponseEntity.ok(reviewService.filterReviews(productName, minStar, maxStar, sort));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse> reviewDetails(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.reviewDetails(id));
    }

}
