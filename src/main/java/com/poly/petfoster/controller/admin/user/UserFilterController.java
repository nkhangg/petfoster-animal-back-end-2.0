package com.poly.petfoster.controller.admin.user;

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
import com.poly.petfoster.service.admin.order.OrderFilterService;
import com.poly.petfoster.service.admin.user.UserFilterService;

@RestController
@RequestMapping("/admin/users")
public class UserFilterController {

    @Autowired
    UserFilterService userFilterService;

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> filterOrders(
            @RequestParam("username") Optional<String> username,
            @RequestParam("fullname") Optional<String> fullname,
            @RequestParam("email") Optional<String> email,
            @RequestParam("gender") Optional<String> gender,
            @RequestParam("phone") Optional<String> phone,
            @RequestParam("role") Optional<String> role) {
        return ResponseEntity.ok(userFilterService.filterUsers(username, fullname, email, gender, phone, role));
    }
}
