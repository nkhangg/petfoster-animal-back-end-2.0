package com.poly.petfoster.service.impl.pet;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.repository.PetRespository;
import com.poly.petfoster.request.pet.CreateaPetRequest;
import com.poly.petfoster.request.pet.UpdatePetRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.homepage.ApiHomePage;
import com.poly.petfoster.response.homepage.PetResponse;
import com.poly.petfoster.service.pet.PetService;
import com.poly.petfoster.ultils.ImageUtils;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PetRespository petRespository;

    @Override
    public ApiResponse selectRecentPet() {

        List<PetResponse> pets = new ArrayList<>();

        petRespository.selectRecentPet().stream().forEach(pet -> {
            pets.add(PetResponse.builder()
                    .id(pet.getPetId())
                    .breed(pet.getPetBreed().getBreedName())
                    .description(pet.getDescriptions())
                    .name(pet.getPetName())
                    .image("https://t0.gstatic.com/licensed-image?q=tbn:ANd9GcQkrjYxSfSHeCEA7hkPy8e2JphDsfFHZVKqx-3t37E4XKr-AT7DML8IwtwY0TnZsUcQ")
                    .type("dog")
                    .fosterDate(300)
                    .fostered(pet.getFosterAt())
                    .sex(pet.getSex() ? "Male" : "Female")
                    .size(pet.getAge())
                    .like(false)
                    .build());

        });

        return ApiResponse.builder()
                .message("Successfully!")
                .status(200)
                .errors(false)
                .data(ApiHomePage.builder().pets(pets).build())
                .build();
    }

    @Override
    public Pet findByAge(Double minAge, Double maxAge) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pet findByCreateAt(Date createAt) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pet findById(String petId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pet findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiResponse updatePet(UpdatePetRequest updatePetRequest) {
        return null;
    }

    @Override
    public ApiResponse createPet(CreateaPetRequest createaPetRequest) {
        Map<String, String> errorsMap = new HashMap<>();
        // start validate

        if (createaPetRequest.getPetName().isEmpty()) {
            errorsMap.put("name", "Name can't be blank");
        }

        if (createaPetRequest.getPetColor().isEmpty()) {
            errorsMap.put("color", "Color can't be blank");
        }

        if (createaPetRequest.getAge().isEmpty()) {
            errorsMap.put("age", "Age can't be blank");
        }

        if (createaPetRequest.getCreateAt().orElse(null) == null) {
            errorsMap.put("create", "Create day can't be blank");
        }

        if (createaPetRequest.getFosterAt().orElse(null) == null) {
            errorsMap.put("foster", "Foster day can't be blank");
        }

        // end validate

        // check errors
        if (!errorsMap.isEmpty()) {
            return ApiResponse.builder()
                    .message("Update faild !")
                    .errors(errorsMap)
                    .status(501)
                    .data(null)
                    .build();
        }

        // Pet newPet =
        // Pet.builder().name(createaPetRequest.getPetName()).age(createaPetRequest.getAge())
        //

        // .build();

        File file = ImageUtils.createFileImage();
        return null;

    }

    @Override
    public ApiResponse deletePet(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePet'");
    }
}
