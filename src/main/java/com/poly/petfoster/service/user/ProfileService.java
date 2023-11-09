package com.poly.petfoster.service.user;

import com.poly.petfoster.request.ProfileRepuest;
import com.poly.petfoster.response.ApiResponse;

public interface ProfileService {

    ApiResponse getProfile(String jwt);

    ApiResponse updateProfile(ProfileRepuest profileRepuest, String token);

}
