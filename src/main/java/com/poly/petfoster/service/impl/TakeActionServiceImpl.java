package com.poly.petfoster.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.OrderDetail;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.entity.ProductRepo;
import com.poly.petfoster.entity.Review;
import com.poly.petfoster.repository.ProductRepoRepository;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.takeaction.BestSellersResponse;
import com.poly.petfoster.response.takeaction.ProductItem;
import com.poly.petfoster.response.takeaction.ReviewItem;
import com.poly.petfoster.response.takeaction.TakeActionResponse;
import com.poly.petfoster.service.TakeActionService;
import com.poly.petfoster.ultils.FormatUtils;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class TakeActionServiceImpl implements TakeActionService {

    @Autowired
    private PortUltil portUltil;

    @Autowired
    private FormatUtils formatUtils;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepoRepository productRepoRepository;

    @Override
    public ApiResponse homePageTakeAction() {

        List<ProductItem> newArricals = new ArrayList<>();
        productRepository.selectNewArrivals().stream().forEach((product) -> {
            newArricals.add(this.createProductTakeAction(product));
        });

        if (newArricals.isEmpty()) {
            return ApiResponse.builder()
                    .message(RespMessage.INTERNAL_SERVER_ERROR.getValue())
                    .data(newArricals)
                    .errors(true)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
        ;
        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .errors(false)
                .status(HttpStatus.OK.value())
                .data(TakeActionResponse.builder()
                        .newArrivals(newArricals)
                        .build())
                .build();
    }

    @Override
    public ApiResponse bestSellers(Optional<Integer> page) {

        List<ProductItem> bestSellers = new ArrayList<>();
        Pageable pageable = PageRequest.of(page.orElse(0), 16);
        List<Product> contents = productRepository.findAllProducts();

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), contents.size());

        if (startIndex >= endIndex) {
            return ApiResponse.builder()
                    .message(RespMessage.NOT_FOUND.getValue())
                    .data(null)
                    .errors(true)
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        List<Product> pageContent = contents.subList(startIndex, endIndex);

        Page<Product> pagination = new PageImpl<Product>(pageContent, pageable, contents.size());

        pagination.getContent().stream().forEach((product) -> {
            ProductItem productTakeAction = this.createProductTakeAction(product);
            bestSellers.add(productTakeAction);
        });

        return ApiResponse.builder()
                .message(RespMessage.SUCCESS.getValue())
                .status(HttpStatus.OK.value())
                .errors(false)
                .data(BestSellersResponse.builder()
                        .data(bestSellers)
                        .pages(pagination.getTotalPages())
                        .build())
                .build();
    }

    public ProductItem createProductTakeAction(Product product) {
        String image = "";
        int discount = 8;
        // int rating = 5;

        List<Review> reviews = product.getReviews();
        Double rating = reviews.stream().mapToDouble(Review::getRate).average().orElse(0.0);
        List<ReviewItem> reviewItems = new ArrayList<>();

        reviews.forEach(review -> {

            List<Integer> sizes = new ArrayList<>();
            List<OrderDetail> orderDetails = review.getOrder().getOrderDetails();
            orderDetails.forEach(item -> {
                sizes.add(item.getProductRepo().getSize());
            });

            reviewItems.add(ReviewItem.builder()
                    .id(review.getId())
                    .avatar(review.getUser().getAvatar() == null ? null
                            : portUltil.getUrlImage(review.getUser().getAvatar()))
                    .name(review.getUser().getUsername())
                    .rating(review.getRate())
                    .sizes(sizes)
                    .comment(review.getComment())
                    .createAt(formatUtils.dateToString(review.getCreateAt(), "MMM d, yyyy"))
                    .build());
        });

        if (!product.getImgs().isEmpty()) {
            image = product.getImgs().get(0).getNameImg();
        }

        List<Integer> sizes = productRepoRepository.findSizeByProduct(product.getId());

        ProductRepo minRepo = productRepoRepository.findByProductMinPrice(product.getId());

        // ProductRepo minPrice =
        // productRepoRepository.findByProductMinPrice(product.getId());

        return ProductItem
                .builder()
                .id(product.getId())
                .size(sizes)
                .discount(discount)
                .image(portUltil.getUrlImage(image))
                .name(product.getName())
                .brand(product.getBrand().getBrand())
                .price(minRepo.getOutPrice().intValue())
                .oldPrice((int) (minRepo.getOutPrice() + (minRepo.getOutPrice() * (discount / 100.0))))
                .rating(rating)
                .reviews(reviews.size())
                .reviewItems(reviewItems)
                .build();
    }

}
