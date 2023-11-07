package com.poly.petfoster.response.order_history;

import java.util.Date;
import java.util.List;

import com.poly.petfoster.entity.Product;
import com.poly.petfoster.response.takeaction.ProductItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistory {
    
    private Integer id;
    private String datePlace;
    private String state;
    private String stateMessage;
    private Double total;
    private List<OrderProductItem> products;

}
