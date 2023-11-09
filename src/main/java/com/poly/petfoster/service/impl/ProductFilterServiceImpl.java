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
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.repository.ProductRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.productfilter.ProductFilterResponse;
import com.poly.petfoster.response.takeaction.ProductItem;
import com.poly.petfoster.service.ProductFilterService;

@Service
public class ProductFilterServiceImpl implements ProductFilterService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TakeActionServiceImpl takeActionServiceImpl;

    @Override
    public ApiResponse filterProducts(Optional<String> typeName, Optional<Double> minPrice, Optional<Double> maxPrice, Optional<Boolean> stock, Optional<String> brand, Optional<String> productName, Optional<String> sort, Optional<Integer> page) {

        List<ProductItem> productItems = new ArrayList<>();
        List<Product> filterProducts =  productRepository.filterProducts(typeName.orElse(null), minPrice.orElse(null), maxPrice.orElse(null), stock.orElse(null), brand.orElse(null), productName.orElse(null), sort.orElse(null));
        Pageable pageable = PageRequest.of(page.orElse(0), 9);

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), filterProducts.size());

        if(startIndex >= endIndex){
            return ApiResponse.builder()
            .message(RespMessage.NOT_FOUND.getValue())
            .data(null)
            .errors(true)
            .status(HttpStatus.NOT_FOUND.value())
            .build();
        }

        List<Product> visibleProducts = filterProducts.subList(startIndex, endIndex);
        Page<Product> pagination = new PageImpl<Product>(visibleProducts, pageable, filterProducts.size());

        pagination.getContent().stream().forEach((product) -> {
            productItems.add(takeActionServiceImpl.createProductTakeAction(product));
        });

        return ApiResponse.builder().message("Successfully!")
                                    .status(200)
                                    .errors(false)
                                    .data(
                                        ProductFilterResponse.builder().filterProducts(productItems)
                                        .pages(pagination.getTotalPages())
                                        .build()
                                    ).build();
    }
}
        
