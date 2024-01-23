package com.poly.petfoster.service.pets;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.pets.PetResponse;

public interface PetService {
    List<PetResponse> buildPetResponses(List<Pet> petsRaw);

    List<PetResponse> buildPetResponses(List<Pet> petsRaw, User user);

    ApiResponse getDetailPet(String id);

    ApiResponse favorite(String id, String token);

    ApiResponse filterPets(Optional<String> name, Optional<String> typeName, Optional<String> colors,
            Optional<String> age, Optional<Boolean> gender, Optional<String> sort, Optional<Integer> page);

    ApiResponse getAttributes();

    ApiResponse filterAdminPets(Optional<String> name, Optional<String> typeName, Optional<String> colors,
            Optional<String> age, Optional<Boolean> gender, Optional<String> status, Optional<Date> minDate,
            Optional<Date> maxDate, Optional<String> sort, Optional<Integer> page);

    public ApiResponse getFavorites(String token, int page);
}
