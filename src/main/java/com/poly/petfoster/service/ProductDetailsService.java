package com.poly.petfoster.service;

import com.poly.petfoster.response.ApiResponse;

public interface ProductDetailsService {
    
    public ApiResponse productDetails(String id);

    public ApiResponse getProductTypesAndBrands();

}
