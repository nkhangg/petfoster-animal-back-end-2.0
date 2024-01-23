package com.poly.petfoster.service.impl.pets;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import com.poly.petfoster.entity.PetBreed;
import com.poly.petfoster.entity.PetType;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.AdoptRepository;
import com.poly.petfoster.repository.FavoriteRepository;
import com.poly.petfoster.repository.PetBreedRepository;
import com.poly.petfoster.repository.PetRepository;
import com.poly.petfoster.repository.PetTypeRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.pages.PetDetailPageResponse;
import com.poly.petfoster.response.pages.homepage.HomePageResponse;
import com.poly.petfoster.response.pets.PetAttributeReponse;
import com.poly.petfoster.response.pets.PetAttributesReponse;
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

    @Autowired
    private PetBreedRepository petBreedRepository;

    @Autowired
    private AdoptRepository adoptRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

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
        boolean canAdopt = adoptRepository.existsByPet(pet.getPetId()) == null;
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
                .canAdopt(canAdopt)
                .build();

    }

    public PetDetailResponse buildPetResponses(Pet pet, User user) {
        Integer fosterDay = (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - pet.getFosterAt().getTime());
        boolean liked = favoriteRepository.existByUserAndPet(user.getId(), pet.getPetId()) != null;
        boolean canAdopt = adoptRepository.existsByPet(pet.getPetId()) == null;

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
                .canAdopt(canAdopt)
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
    public ApiResponse filterPets(Optional<String> name, Optional<String> typeName, Optional<String> colors,
            Optional<String> age, Optional<Boolean> gender, Optional<String> sort, Optional<Integer> page) {

        List<Pet> filterPets = petRepository.filterPets(name.orElse(null), typeName.orElse(null), colors.orElse(null),
                age.orElse(null), gender.orElse(null), sort.orElse("latest"));

        Integer pageSize = 9;
        Integer pages = page.orElse(0);
        Integer totalPages = (filterPets.size() + pageSize - 1) / pageSize;

        if (pages >= totalPages) {
            return ApiResponse.builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("No data available!!!")
                    .errors(false)
                    .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                    .build();
        }

        Pageable pageable = PageRequest.of(pages, pageSize);
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
        List<PetResponse> pets;

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader("Authorization");

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
            pets = this.buildPetResponses(visiblePets, user);

        } else {
            pets = this.buildPetResponses(visiblePets);
        }

        return ApiResponse.builder()
                .status(200)
                .message("Successfully!!!")
                .errors(false)
                .data(PagiantionResponse.builder().data(pets).pages(pagination.getTotalPages()).build())
                .build();
    }

    @Override
    public ApiResponse getAttributes() {

        List<Pet> list = petRepository.findAll();

        List<PetAttributeReponse> colors_ = new ArrayList<>();
        List<PetAttributeReponse> states_ = new ArrayList<>();
        List<PetAttributeReponse> breeds = new ArrayList<>();
        List<PetAttributeReponse> typies = new ArrayList<>();

        for (Pet p : list) {
            colors_.add(new PetAttributeReponse(p.getPetColor().toLowerCase(), p.getPetColor()));
            if (p.getAdoptStatus().equalsIgnoreCase("Awaiting adoption")) {
                states_.add(new PetAttributeReponse("awaiting", p.getAdoptStatus()));
            } else if (p.getAdoptStatus().equalsIgnoreCase("Haven't adopted yet")) {
                states_.add(new PetAttributeReponse("haven_t", p.getAdoptStatus()));
            } else if (p.getAdoptStatus().equalsIgnoreCase("Adopted")) {
                states_.add(new PetAttributeReponse("adopted", p.getAdoptStatus()));
            }
        }
        List<PetAttributeReponse> colors = new ArrayList<>(
                new HashSet<>(colors_));
        List<PetAttributeReponse> states = new ArrayList<>(
                new HashSet<>(states_));

        for (PetBreed b : petBreedRepository.findAll()) {
            breeds.add(new PetAttributeReponse(b.getBreedId(), b.getBreedName()));
        }

        for (PetType t : petTypeRepository.findAll()) {
            typies.add(new PetAttributeReponse(t.getId(), t.getName()));
        }
        PetAttributesReponse attributes = PetAttributesReponse.builder().breeds(breeds)
                .colors(colors).states(states).typies(typies).build();

        return ApiResponse.builder().message("Successfully!").errors(Boolean.FALSE).status(HttpStatus.OK.value())
                .data(attributes).build();
    }

    @Override
    public ApiResponse filterAdminPets(Optional<String> name, Optional<String> typeName, Optional<String> colors,
            Optional<String> age, Optional<Boolean> gender, Optional<String> status, Optional<Date> minDate,
            Optional<Date> maxDate, Optional<String> sort, Optional<Integer> page) {

        Date minDateValue = minDate.orElse(null);
        Date maxDateValue = maxDate.orElse(null);

        if (minDateValue == null && maxDateValue != null) {
            minDateValue = maxDateValue;
        }

        if (maxDateValue == null && minDateValue != null) {
            maxDateValue = minDateValue;
        }

        if (minDateValue != null && maxDateValue != null) {
            if (minDateValue.after(maxDateValue)) {
                return ApiResponse.builder()
                        .message("The max date must after the min date!!!")
                        .status(HttpStatus.CONFLICT.value())
                        .errors("The max date must after the min date!!!")
                        .build();
            }
        }

        List<Pet> filterPets = petRepository.filterAdminPets(name.orElse(null), typeName.orElse(null),
                colors.orElse(null),
                age.orElse(null), gender.orElse(null), status.orElse(null), minDateValue, maxDateValue,
                sort.orElse("latest"));

        Integer pageSize = 10;
        Integer pages = page.orElse(0);
        Integer totalPages = (filterPets.size() + pageSize - 1) / pageSize;

        if (pages >= totalPages) {
            return ApiResponse.builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("No data available!!!")
                    .errors(false)
                    .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                    .build();
        }

        Pageable pageable = PageRequest.of(pages, pageSize);
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
        List<PetDetailResponse> pets = new ArrayList<>();
        visiblePets.forEach(pet -> pets.add(this.buildPetResponses(pet)));

        return ApiResponse.builder()
                .status(200)
                .message("Successfully!!!")
                .errors(false)
                .data(PagiantionResponse.builder().data(pets).pages(totalPages).build())
                .build();
    }

}
