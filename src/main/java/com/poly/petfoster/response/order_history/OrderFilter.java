package com.poly.petfoster.response.order_history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderFilter {
    
    private Integer orderId;

    private String username;

    private Integer total;

    private String placedDate;

    private String status;

}
