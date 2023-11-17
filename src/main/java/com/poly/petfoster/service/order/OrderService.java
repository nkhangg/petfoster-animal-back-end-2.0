package com.poly.petfoster.service.order;

import java.util.Optional;

import com.poly.petfoster.request.order.OrderRequest;
import com.poly.petfoster.request.payments.PaymentRequest;
import com.poly.petfoster.response.ApiResponse;

public interface OrderService {

    public ApiResponse order(String jwt, OrderRequest orderRequest);

    // public ApiResponse orderHistory(String jwt, Optional<Integer> page);
    public ApiResponse orderHistory(String jwt, Optional<Integer> page, Optional<String> status);

    public ApiResponse payment(PaymentRequest paymentRequest);
}
