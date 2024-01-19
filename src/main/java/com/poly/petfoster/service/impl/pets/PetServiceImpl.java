package com.poly.petfoster.service.impl.pets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Favorite;
import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.FavoriteRepository;
import com.poly.petfoster.repository.PetRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.pages.PetDetailPageResponse;
import com.poly.petfoster.response.pets.PetDetailResponse;
import com.poly.petfoster.response.pets.PetResponse;
import com.poly.petfoster.service.pets.PetService;
import com.poly.petfoster.service.user.UserService;
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

    @Autowired
    private UserService userService;

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
                .sterilization(pet.getIsSpay() ? "sterilizated" : "not sterilization")
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
                .sterilization(pet.getIsSpay() ? "sterilizated" : "not sterilization")
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

    @Override
    public ApiResponse favorite(String id, String token) {

        User user = userService.getUserFromToken(token);

        if (id == null || id.isEmpty()) {
            return ApiResponse.builder().message("Failure").status(HttpStatus.BAD_REQUEST.value()).errors(true)
                    .data(null).build();
        }

        Pet pet = petRepository.findById(id).orElse(null);

        // check user
        if (user == null || pet == null) {
            return ApiResponse.builder().message("Failure").status(HttpStatus.BAD_REQUEST.value()).errors(true)
                    .data(null).build();
        }

        Favorite isFavorite = favoriteRepository.existByUserAndPet(user.getId(), pet.getPetId());

        Favorite favoriteResponse = isFavorite;
        if (isFavorite == null) {
            favoriteResponse = Favorite.builder().user(user).pet(pet).build();
            if (favoriteResponse != null) {
                favoriteRepository.save(favoriteResponse);
            }
        } else {
            if (favoriteResponse != null) {
                favoriteRepository.delete(favoriteResponse);
            }
        }

        return ApiResponse.builder()
                .data(favoriteResponse)
                .status(HttpStatus.OK.value())
                .errors(false)
                .message(isFavorite == null ? "Favorite Successfuly" : "Unfavorite Successfuly")
                .build();

    }


    @Override
    public ApiResponse filterPets(Optional<String> name, Optional<String> typeName, Optional<String> colors, Optional<String> age, Optional<Boolean> gender,Optional<String> sort, Optional<Integer> page) {

        List<Pet> filterPets = petRepository.filterPets(name.orElse(null), typeName.orElse(null), colors.orElse(null), age.orElse(null), gender.orElse(null), sort.orElse("latest"));

        Pageable pageable = PageRequest.of(page.orElse(0), 9);
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), filterPets.size());
        if (startIndex >= endIndex) {
            return ApiResponse.builder()
            .status(200)
            .message("Successfully!!!")
            .errors(false)
            .data(PagiantionResponse.builder().data(filterPets).pages(0).build())
            .build();
        }

        List<Pet> visiblePets = filterPets.subList(startIndex, endIndex);
        Page<Pet> pagination = new PageImpl<Pet>(visiblePets, pageable, filterPets.size());
        List<PetResponse> pets = this.buildPetResponses(visiblePets);

        return ApiResponse.builder()
            .status(200)
            .message("Successfully!!!")
            .errors(false)
            .data(PagiantionResponse.builder().data(pets).pages(pagination.getTotalPages()).build())
            .build();
    }

}
