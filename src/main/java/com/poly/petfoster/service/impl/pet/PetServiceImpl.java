package com.poly.petfoster.service.impl.pet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.Pet;
import com.poly.petfoster.entity.Product;
import com.poly.petfoster.repository.PetRespository;
import com.poly.petfoster.request.pet.CreateaPetRequest;
import com.poly.petfoster.request.pet.UpdatePetRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.homepage.ApiHomePage;
import com.poly.petfoster.response.homepage.PetResponse;
import com.poly.petfoster.response.pet_details.PetDetail;
import com.poly.petfoster.response.product_details.ProductDetail;
import com.poly.petfoster.response.product_details.SizeAndPrice;
import com.poly.petfoster.response.takeaction.ProductItem;
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
    public ApiResponse petDetail(String petId) {
        Map<String, String> errorMap = new HashMap<>();

        Pet p = petRespository.findById(petId).orElse(null);

        if (p == null) {
            errorMap.put("pet id", "pet id is not exists!!!");
            return ApiResponse.builder()
                    .message("Failure!!!")
                    .status(400)
                    .errors(errorMap)
                    .build();
        }

        PetDetail petDetail = new PetDetail();

        petDetail.setId(petId);
        petDetail.setName(p.getPetName());
        petDetail.setBreed(p.getPetBreed().getBreedName().toString());
        petDetail.setImg(p.getImgs().get(0).getNameImg());
        petDetail.setDescription(p.getDescriptions());
        petDetail.setFostered(p.getFosterAt());
        petDetail.setSize(p.getAge());
        petDetail.setSex(p.getSex());
        petDetail.setColor(p.getPetColor());
        petDetail.setSterilizated(p.getIsSpay());
        petDetail.setVaccinated(p.getVaccination());
        petDetail.setFosterDate((int) (new Date().getTime() -
                p.getFosterAt().getTime()) / (24 * 3600 * 1000));
        List<String> imgs = new ArrayList<>();

        p.getImgs().forEach(img -> {
            imgs.add(img.getNameImg());
        });

        petDetail.setImgs(imgs);
        petDetail.setType(p.getPetBreed().getPetType().getName().toString());

        return ApiResponse.builder().message("Successfully!").status(200).errors(false)
                .data(petDetail).build();
    }

    @Override
    public Pet findByName(String name) {
        // TODO Auto-generated method stub
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

    @Override
    public ApiResponse updatePet(UpdatePetRequest updatePetRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePet'");
    }
}
