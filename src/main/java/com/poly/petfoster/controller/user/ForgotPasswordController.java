package com.poly.petfoster.controller.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.petfoster.request.ResetPasswordRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.user.UserService;

@Controller
@RequestMapping("/api/")
public class ForgotPasswordController {
    @Autowired
    UserService userService;

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(userService.updatePassword(resetPasswordRequest));
    }
}
