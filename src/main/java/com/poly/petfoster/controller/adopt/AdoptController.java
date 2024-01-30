package com.poly.petfoster.controller.adopt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.poly.petfoster.request.adopts.AdoptsRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.adopt.AdoptService;

@RestController
@RequestMapping("/api/user/adopts")
public class AdoptController {

    @Autowired
    AdoptService adoptService;
    
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAdopts(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(adoptService.getAdopts(jwt));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> adopt(@RequestHeader("Authorization") String jwt, @RequestBody AdoptsRequest adoptsRequest) {
        return ResponseEntity.ok(adoptService.adopt(jwt, adoptsRequest));
    }

}
