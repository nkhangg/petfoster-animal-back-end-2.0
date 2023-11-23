package com.poly.petfoster.service.admin.review;

import java.util.Optional;

import com.poly.petfoster.response.ApiResponse;

public interface AdminReviewService {

    ApiResponse filterReviews(Optional<String> name, Optional<Integer> minStar, Optional<Integer> maxStar, Optional<String> sort);

    ApiResponse reviewDetails(String productId);
    
}
