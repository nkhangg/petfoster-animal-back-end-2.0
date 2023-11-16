package com.poly.petfoster.controller.order;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.order.OrderRequest;
import com.poly.petfoster.request.payments.PaymentRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.order.OrderService;

@RestController
@RequestMapping("/api/")
public class OrderController {
    
    @Autowired
    OrderService orderService;

    @PostMapping("user/order")
    public ResponseEntity<ApiResponse> order(@RequestHeader("Authorization") String jwt, @Valid @RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.order(jwt, orderRequest));
    }

    @GetMapping("user/order/history")
    public ResponseEntity<ApiResponse> ordersHistory(@RequestHeader("Authorization") String jwt, @RequestParam("page") Optional<Integer> page) {
        return ResponseEntity.ok(orderService.orderHistory(jwt, page));
    }

    @PostMapping("payment")
    public ResponseEntity<ApiResponse> payment(@Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(orderService.payment(paymentRequest));
    }
}
