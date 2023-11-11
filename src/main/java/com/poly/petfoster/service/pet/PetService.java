package com.poly.petfoster.service.pet;

import java.util.Date;

import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.request.pet.CreateaPetRequest;
import com.poly.petfoster.request.pet.UpdatePetRequest;
import com.poly.petfoster.response.ApiResponse;

public interface PetService {

    public Pet findById(String petId);

    public Pet findByName(String name);

    public Pet findByCreateAt(Date createAt);

    public Pet findByAge(Double minAge, Double maxAge);

    public ApiResponse selectRecentPet();

    public ApiResponse updatePet(UpdatePetRequest updatePetRequest);

    public ApiResponse createPet(CreateaPetRequest createaPetRequest);

    public ApiResponse deletePet(String id);

}
