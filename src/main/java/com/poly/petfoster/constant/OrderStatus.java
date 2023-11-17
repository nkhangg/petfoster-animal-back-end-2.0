package com.poly.petfoster.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    WAITING("Waiting"),
    PLACED("Placed"),
    SHIPPING("Shipping"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    private final String state;

    public String getValue() { return state; }

}
