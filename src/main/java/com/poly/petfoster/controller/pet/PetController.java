package com.poly.petfoster.controller.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pet.PetService;

@Controller
@RequestMapping("/api/pet/")
public class PetController {
    @Autowired
    PetService petService;

    @GetMapping("/pet-detail")
    public ResponseEntity<ApiResponse> pet_detail(@RequestParam("petID") String petID) {
        return ResponseEntity.ok(petService.petDetail(petID));
    }
}
