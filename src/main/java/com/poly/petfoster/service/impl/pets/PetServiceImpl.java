package com.poly.petfoster.service.impl.pets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.FavoriteRepository;
import com.poly.petfoster.repository.PetRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.pages.PetDetailPageResponse;
import com.poly.petfoster.response.pages.homepage.HomePageResponse;
import com.poly.petfoster.response.pets.PetDetailResponse;
import com.poly.petfoster.response.pets.PetResponse;
import com.poly.petfoster.service.pets.PetService;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PortUltil portUltil;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<PetResponse> buildPetResponses(List<Pet> petsRaw, User user) {

        return petsRaw.stream().map(pet -> {
            boolean liked = favoriteRepository.existByUserAndPet(user.getId(), pet.getPetId()) != null;

            Integer fosterDay = (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - pet.getFosterAt().getTime());

            return PetResponse.builder()
                    .id(pet.getPetId())
                    .breed(pet.getPetBreed().getBreedName())
                    .name(pet.getPetName())
                    .image(portUltil.getUrlImage(pet.getImgs().get(0).getNameImg()))
                    .description(pet.getDescriptions() == null ? "" : pet.getDescriptions())
                    .fosterDate(fosterDay)
                    .size(pet.getAge())
                    .sex(pet.getSex() ? "male" : "female")
                    .type(pet.getPetBreed().getPetType().getName())
                    .like(liked)
                    .fostered(pet.getFosterAt())
                    .build();
        }).toList();
    }

    @Override
    public List<PetResponse> buildPetResponses(List<Pet> petsRaw) {
        return petsRaw.stream().map(pet -> {

            Integer fosterDay = (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - pet.getFosterAt().getTime());

            return PetResponse.builder()
                    .id(pet.getPetId())
                    .breed(pet.getPetBreed().getBreedName())
                    .name(pet.getPetName())
                    .image(portUltil.getUrlImage(pet.getImgs().get(0).getNameImg()))
                    .description(pet.getDescriptions() == null ? "" : pet.getDescriptions())
                    .fosterDate(fosterDay)
                    .size(pet.getAge())
                    .sex(pet.getSex() ? "male" : "female")
                    .type(pet.getPetBreed().getPetType().getName())
                    .like(false)
                    .fostered(pet.getFosterAt())
                    .build();
        }).toList();
    }

    public PetDetailResponse buildPetResponses(Pet pet) {
        Integer fosterDay = (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - pet.getFosterAt().getTime());

        List<String> images = pet.getImgs().stream().map(image -> {

            return portUltil.getUrlImage(image.getNameImg());
        }).toList();

        return PetDetailResponse.builder()
                .id(pet.getPetId())
                .breed(pet.getPetBreed().getBreedName())
                .name(pet.getPetName())
                .image(portUltil.getUrlImage(pet.getImgs().get(0).getNameImg()))
                .description(pet.getDescriptions() == null ? "" : pet.getDescriptions())
                .fosterDate(fosterDay)
                .size(pet.getAge())
                .sex(pet.getSex() ? "male" : "female")
                .type(pet.getPetBreed().getPetType().getName())
                .like(false)
                .fostered(pet.getFosterAt())
                .images(images)
                .color(pet.getPetColor())
                .build();

    }

    public PetDetailResponse buildPetResponses(Pet pet, User user) {
        Integer fosterDay = (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - pet.getFosterAt().getTime());
        boolean liked = favoriteRepository.existByUserAndPet(user.getId(), pet.getPetId()) != null;
        List<String> images = pet.getImgs().stream().map(image -> {

            return portUltil.getUrlImage(image.getNameImg());
        }).toList();

        return PetDetailResponse.builder()
                .id(pet.getPetId())
                .breed(pet.getPetBreed().getBreedName())
                .name(pet.getPetName())
                .image(portUltil.getUrlImage(pet.getImgs().get(0).getNameImg()))
                .description(pet.getDescriptions() == null ? "" : pet.getDescriptions())
                .fosterDate(fosterDay)
                .size(pet.getAge())
                .sex(pet.getSex() ? "male" : "female")
                .type(pet.getPetBreed().getPetType().getName())
                .like(liked)
                .fostered(pet.getFosterAt())
                .images(images)
                .color(pet.getPetColor())
                .build();

    }

    @Override
    public ApiResponse getDetailPet(String id) {

        // get token from headers. Can't use @RequestHeader("Authorization") because
        // when call api if have'nt token is throw error
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader("Authorization");

        if (id == null || id.isEmpty()) {
            return ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errors(true)
                    .message("Failure")
                    .build();
        }

        Pet pet = petRepository.findById(id).orElse(null);

        if (pet == null) {
            return ApiResponse.builder()
                    .data(null)
                    .status(HttpStatus.NOT_FOUND.value())
                    .errors(true)
                    .message("Failure")
                    .build();
        }

        List<Pet> othersPetRaw = petRepository.findByPetStyleAndIgnorePetId(pet.getPetBreed().getBreedId(),
                pet.getAge(),
                pet.getPetId());

        if (token != null) {

            // get username from token requested to user
            String username = jwtProvider.getUsernameFromToken(token);

            // check username
            if (username == null || username.isEmpty()) {
                return ApiResponse.builder().message("Failure").status(HttpStatus.BAD_REQUEST.value()).errors(true)
                        .data(new ArrayList<>()).build();
            }

            // get user to username
            User user = userRepository.findByUsername(username).orElse(null);

            // check user
            if (user == null) {
                return ApiResponse.builder().message("Failure").status(HttpStatus.BAD_REQUEST.value()).errors(true)
                        .data(new ArrayList<>()).build();
            }

            // get pets
            PetDetailResponse petResponse = this.buildPetResponses(pet, user);

            List<PetResponse> others = this.buildPetResponses(othersPetRaw, user);

            return ApiResponse.builder().message("Successfuly").status(200).errors(false)
                    .data(PetDetailPageResponse.builder().pet(petResponse).orthers(others).build()).build();
        }

        PetDetailResponse petResponse = this.buildPetResponses(pet);

        List<PetResponse> others = this.buildPetResponses(othersPetRaw);

        return ApiResponse.builder()
                .data(PetDetailPageResponse.builder().pet(petResponse).orthers(others).build())
                .status(HttpStatus.OK.value())
                .errors(false)
                .message("Successfuly")
                .build();
    }

}