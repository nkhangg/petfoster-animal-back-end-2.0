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

    @NotBlank(message = "Phone number can't be blank!!!")
    private String phone;

    @NotBlank(message = "Address can't be blank!!!")
    private String address;
}
