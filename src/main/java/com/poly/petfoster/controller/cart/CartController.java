package com.poly.petfoster.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.carts.CartRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.carts.CartService;

@RestController
@RequestMapping("/api/user/")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("carts")
    public ResponseEntity<ApiResponse> getCarts(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(cartService.getCarts(jwt));
    }

    @PutMapping("carts")
    public ResponseEntity<ApiResponse> updateCarts(@RequestHeader("Authorization") String jwt,
            @RequestBody CartRequest cartRequest) {
        return ResponseEntity.ok(cartService.updateCarts(jwt, cartRequest.getId(), cartRequest.getQuantity()));
    }
}
