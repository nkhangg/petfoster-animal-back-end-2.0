package com.poly.petfoster.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactItemResponse {
    
    private String image;

    private int quantity;

    private String title;

}
