package com.poly.petfoster.response.pets;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetFilterResponse {
    
    List<PetResponse> filterPets;

    private Integer pages;

}
