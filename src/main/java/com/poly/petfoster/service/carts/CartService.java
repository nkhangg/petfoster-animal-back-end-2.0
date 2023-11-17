package com.poly.petfoster.service.carts;

import com.poly.petfoster.response.ApiResponse;

public interface CartService {
    ApiResponse getCarts(String jwt);

    ApiResponse updateCarts(String jwt, String productID, Integer quantity);
}
