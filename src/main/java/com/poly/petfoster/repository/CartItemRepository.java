package com.poly.petfoster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.petfoster.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(nativeQuery = true, value = "select * from cart_item where cart_id = :cartId")
    List<CartItem> cartItem(@Param("cartId") Integer cartId);
}
