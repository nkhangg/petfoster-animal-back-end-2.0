package com.poly.petfoster.controller.pets;

import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pets.PetService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/pets/")
public class PetAttrributesController {
    @Autowired
    PetService petService;

    @GetMapping("attributes")
    public ResponseEntity<ApiResponse> getAttributes() {
        return ResponseEntity.ok(petService.getAttributes());
    }
}
