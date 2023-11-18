package com.poly.petfoster.response.order_history;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {

    private Integer id;

    private String address;

    private String placedDate;

    private String deliveryMethod;

    private String name;

    private String paymentMethod;

    private String phone;

    private List<OrderProductItem> products;

    private Integer shippingFee;

    private Integer subTotal;

    private Integer quantity;

    private Integer total;

    private String state;

}
