package com.poly.petfoster.request.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInfoRequest {
    private String id;
    private String name;
    private String brand;
    private String type;
    private String description;
}
