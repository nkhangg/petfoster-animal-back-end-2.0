package com.poly.petfoster.service.pets;

import java.util.List;

import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.pets.PetResponse;

public interface PetService {
    List<PetResponse> buildPetResponses(List<Pet> petsRaw);

    List<PetResponse> buildPetResponses(List<Pet> petsRaw, User user);

    ApiResponse getDetailPet(String id);
}
