package com.poly.petfoster.response.pets;

import java.util.List;

import com.poly.petfoster.entity.PetBreed;
import com.poly.petfoster.entity.PetType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDataFilterReponse {
    List<PetType> petTypes;
    List<PetBreed> petBreeds;
    List<PetColor> petColors;
}
