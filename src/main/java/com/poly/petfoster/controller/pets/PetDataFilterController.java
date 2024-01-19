package com.poly.petfoster.controller.pets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.pets.PetFilterDataService;
import com.poly.petfoster.service.pets.PetService;

@RestController
@RequestMapping("api/pet")
public class PetDataFilterController {
    @Autowired
    PetFilterDataService petFilterDataService;

    @GetMapping("/data")
    public ResponseEntity<ApiResponse> getDataForFilter() {
        return ResponseEntity.ok(petFilterDataService.getDataForFilter());
    }
}
