package com.poly.petfoster.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pet.PetService;

@RestController
@RequestMapping("/api/")
public class HomeController {

    @Autowired
    PetService petService;

    @GetMapping("home")
    public ResponseEntity<ApiResponse> home() {
        return ResponseEntity.ok(petService.selectRecentPet());
    }

}
