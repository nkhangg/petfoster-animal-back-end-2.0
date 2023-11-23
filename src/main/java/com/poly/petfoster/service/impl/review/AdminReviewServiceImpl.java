package com.poly.petfoster.service.impl.review;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.Review;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.repository.ReviewRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.review.DetailRate;
import com.poly.petfoster.response.review.HasReplyReviews;
import com.poly.petfoster.response.review.ReviewDetailsResponse;
import com.poly.petfoster.response.review.ReviewFilterResponse;
import com.poly.petfoster.response.takeaction.ProductItem;
import com.poly.petfoster.response.takeaction.ReviewItem;
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

        // List<Product> products = productRepository.getProductsReview();
        List<Product> products = productRepository.findAll();
       
        if(name != null) {
            products = productRepository.getProductsByNameInReview(name);
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
                .rate(productItem.getRating() != null ? productItem.getRating() : 0)
                .lastest(reviewRepository.getLastestReviewByProduct(product.getId()) != null ? reviewRepository.getLastestReviewByProduct(product.getId()).getCreateAt() : null)
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
            case "latest-asc":
                filterReviews.sort(Comparator.comparing(review -> review.getLastest() != null ? review.getLastest() : Constant.MIN_DATE));
                break;
            case "latest-desc":
                filterReviews.sort(Comparator.comparing((ReviewFilterResponse review) -> review.getLastest() != null ? review.getLastest() : Constant.MIN_DATE).reversed());
                break;
            default:
                break;
        }
        
        return ApiResponse.builder().message("Successfully").status(200).errors(false).data(filterReviews).build();
    }

    @Override
    public ApiResponse reviewDetails(String productId) {
        
        Product product = productRepository.findById(productId).orElse(null);

        if(product == null) {
            return ApiResponse.builder()
                    .message("Product not found")
                    .status(404)
                    .errors("Product not found")
                    .build();
        }

        List<Review> reviews = product.getReviews();
        // List<Review> noRelpyReviews = reviewRepository.getNoReplyReivewsByProduct(product.getId());
        ProductItem productItem = takeActionServiceImpl.createProductTakeAction(product);
        DetailRate detailRate = this.createDetailRate(reviews);
        
        List<ReviewItem> reviewItems = takeActionServiceImpl.getReviewItems(reviews, product);

        ReviewDetailsResponse reviewResponse = ReviewDetailsResponse.builder()
        .id(productId)
        .name(productItem.getName())
        .image(productItem.getImage())
        .rate(productItem.getRating())
        .detailRate(detailRate)
        .totalRate(reviews.size())
        .reviews(reviewItems)
        .build();
               
        return ApiResponse.builder().message("Successfully").status(200).errors(false).data(reviewResponse).build();
    }

    private DetailRate createDetailRate(List<Review> reviews) {

        return DetailRate.builder()
        .one(getRateNumber(reviews, 1))
        .two(getRateNumber(reviews, 2))
        .three(getRateNumber(reviews, 3))
        .four(getRateNumber(reviews, 4))
        .five(getRateNumber(reviews, 5))
        .build();
    }

    private Integer getRateNumber(List<Review> reviews, Integer rate) {
        return reviews.stream().filter(review -> review.getRate() == rate).collect(Collectors.toList()).size();
    }
    
}
