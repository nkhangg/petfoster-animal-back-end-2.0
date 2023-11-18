package com.poly.petfoster.service.admin.user;

import java.util.Optional;

import com.poly.petfoster.response.ApiResponse;

public interface UserFilterService {

    ApiResponse filterUsers(Optional<String> username, Optional<String> fullname, Optional<String> email,
            Optional<String> gender, Optional<String> phone, Optional<String> role);

}
