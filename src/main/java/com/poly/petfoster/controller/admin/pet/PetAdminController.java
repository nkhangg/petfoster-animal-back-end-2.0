package com.poly.petfoster.controller.admin.pet;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pets.PetService;

@RestController
@RequestMapping("/api/admin/pets")
public class PetAdminController {
    
    @Autowired
    PetService petService;

    // @GetMapping("")
    // public ResponseEntity<ApiResponse> filterAdminPets(
    //         @RequestParam("name") Optional<String> name,
    //         @RequestParam("typeName") Optional<String> typeName,
    //         @RequestParam("colors") Optional<String> colors,
    //         @RequestParam("age") Optional<String> age,
    //         @RequestParam("gender") Optional<Boolean> gender,
    //         @RequestParam("sort") Optional<String> status,
    //         @RequestParam("sort") Optional<String> sort,
    //         @RequestParam("page") Optional<Integer> page) {
    //     return ResponseEntity.ok(petService.filterAdminPets(name, typeName, colors, age, gender, status, sort, page));
    // }

    @GetMapping("")
    public ResponseEntity<ApiResponse> filterAdminPets(
            @RequestParam("name") Optional<String> name,
            @RequestParam("typeName") Optional<String> typeName,
            @RequestParam("colors") Optional<String> colors,
            @RequestParam("age") Optional<String> age,
            @RequestParam("gender") Optional<Boolean> gender,
            @RequestParam("status") Optional<String> status,
            @RequestParam("minDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> minDate,
            @RequestParam("maxDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> maxDate,
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("page") Optional<Integer> page
            ) {
        return ResponseEntity.ok(petService.filterAdminPets(name, typeName, colors, age, gender, status, minDate, maxDate, sort, page));
    }

}
