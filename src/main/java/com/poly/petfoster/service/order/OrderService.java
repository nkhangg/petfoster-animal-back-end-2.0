package com.poly.petfoster.service.order;

import java.util.Optional;

import com.poly.petfoster.request.OrderRequest;
import com.poly.petfoster.response.ApiResponse;

public interface OrderService {

    // public ApiResponse createOrder(String jwt, OrderRequest orderRequest);

    public ApiResponse orderHistory(String jwt, Optional<Integer> page);
}
