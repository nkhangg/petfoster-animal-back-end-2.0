package com.poly.petfoster.service.impl.pets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.PetBreed;
import com.poly.petfoster.entity.PetType;
import com.poly.petfoster.repository.PetBreedRepository;
import com.poly.petfoster.repository.PetRepository;
import com.poly.petfoster.repository.PetTypeRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.pets.PetColor;
import com.poly.petfoster.response.pets.PetDataFilterReponse;
import com.poly.petfoster.service.pets.PetFilterDataService;

@Service
public class PetServiceFilterDataImpl implements PetFilterDataService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    PetBreedRepository petBreedRepository;
    @Autowired
    PetTypeRepository petTypeRepository;

    @Override
    public ApiResponse getDataForFilter() {
        List<Pet> list = petRepository.findAll();
        List<PetColor> colors = new ArrayList<>();
        List<PetBreed> brees = petBreedRepository.findAll();
        List<PetType> typies = petTypeRepository.findAll();
        for (Pet p : list) {
            colors.add(new PetColor(p.getPetColor(), p.getPetColor()));
        }
        PetDataFilterReponse data = new PetDataFilterReponse();
        data.setPetBreeds(brees);
        data.setPetColors(colors);
        data.setPetTypes(typies);
        return ApiResponse.builder().data(data).build();
    }
}
