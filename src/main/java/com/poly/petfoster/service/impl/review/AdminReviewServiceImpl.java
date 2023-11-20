package com.poly.petfoster.service.impl.review;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.Review;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.ReviewRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.review.ReviewFilterResponse;
import com.poly.petfoster.response.takeaction.ProductItem;
import com.poly.petfoster.service.admin.review.AdminReviewService;
import com.poly.petfoster.service.impl.TakeActionServiceImpl;
import com.poly.petfoster.ultils.FormatUtils;

@Service
public class AdminReviewServiceImpl implements AdminReviewService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    TakeActionServiceImpl takeActionServiceImpl;

    @Autowired
    FormatUtils formatUtils;

    @Override
    public ApiResponse filterReviews(Optional<String> productName, Optional<Integer> minStar, Optional<Integer> maxStar, Optional<String> sort) {
        
        String name = productName.orElse(null);
        Integer min = minStar.orElse(0);
        Integer max = maxStar.orElse(5);
        String customSort = sort.orElse("");

        List<Product> products = new ArrayList<>();
       
        if(name != null) {
            products = productRepository.getProductsByNameInReview(name);
        }

        if(products.isEmpty()) {
            return ApiResponse.builder()
                    .message("No data available")
                    .status(200)
                    .errors(false)
                    .build();
        }

        List<ReviewFilterResponse> reviews = new ArrayList<>();
        products.forEach(product -> {

            List<Review> noRelpyReviews = reviewRepository.getNoReplyReivewsByProduct(product.getId());
            ProductItem productItem = takeActionServiceImpl.createProductTakeAction(product);
            
            reviews.add(
                ReviewFilterResponse.builder()
                .productId(productItem.getId())
                .productName(productItem.getName())
                .image(productItem.getImage())
                .rate(productItem.getRating())
                .lastest(formatUtils.dateToString(reviewRepository.getLastestReviewByProduct(product.getId()).getCreateAt(), "dd-MM-yyyy"))
                .reviews(productItem.getReviews())
                .commentNoRep(noRelpyReviews.size())
                .build()
            );
            
        });

        if(min >= max) {
            return ApiResponse.builder()
                    .message("Max star must larger than Min star")
                    .status(HttpStatus.FAILED_DEPENDENCY.value())
                    .errors("Max star must larger than Min star")
                    .build();
        }

        List<ReviewFilterResponse> filterReviews = new ArrayList<>();
        filterReviews = reviews.stream()
                .filter(review -> review.getRate() >= min && review.getRate() <= max)
                .collect(Collectors.toList());

        switch (customSort) {
            case "rate-asc":
                filterReviews.sort(Comparator.comparingDouble(ReviewFilterResponse::getRate));
                break;
            case "rate-desc":
                filterReviews.sort(Comparator.comparingDouble(ReviewFilterResponse::getRate).reversed());
                break;
            case "review-asc":
                filterReviews.sort(Comparator.comparingDouble(ReviewFilterResponse::getReviews));
                break;
            case "review-desc":
                filterReviews.sort(Comparator.comparingDouble(ReviewFilterResponse::getReviews).reversed());
                break;
            default:
                break;
        }
        
        return ApiResponse.builder().message("Successfully").status(200).errors(false).data(filterReviews).build();
    }
    
}
