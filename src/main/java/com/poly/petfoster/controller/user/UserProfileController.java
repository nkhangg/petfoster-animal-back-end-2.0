package com.poly.petfoster.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.poly.petfoster.request.ProfileRepuest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.user.ProfileService;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(profileService.getProfile(jwt));
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(@ModelAttribute("user") ProfileRepuest profileRepuest,
            @RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(profileService.updateProfile(profileRepuest, jwt));
    }

}
