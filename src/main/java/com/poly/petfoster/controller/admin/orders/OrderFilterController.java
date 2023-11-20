package com.poly.petfoster.controller.admin.orders;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.admin.order.OrderFilterService;

@RestController
@RequestMapping("/api/admin/orders")
public class OrderFilterController {

    @Autowired
    OrderFilterService orderFilterService;
    
    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> filterOrders(
            @RequestParam("username") Optional<String> username,
            @RequestParam("orderId") Optional<Integer> orderId,
            @RequestParam("status") Optional<String> status,
            @RequestParam("minDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> minDate, 
            @RequestParam("maxDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> maxDate,
            @RequestParam("sort") Optional<String> sort) {
        return ResponseEntity.ok(orderFilterService.filterOrders(username, orderId, status, minDate, maxDate, sort));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse> orderDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(orderFilterService.orderDetails(id));
    }
}

