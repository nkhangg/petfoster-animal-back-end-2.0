package com.poly.petfoster.request.adopts;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptsRequest {
    private String userId;
    private String petId;
    private Integer addressId;
}
