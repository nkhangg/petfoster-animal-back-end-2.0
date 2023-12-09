package com.poly.petfoster.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.petfoster.request.ResetPasswordRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/")
public class ForgotPasswordController {
    @Autowired
    UserService userService;

    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(HttpServletRequest req,
            @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(userService.sendCodeForResetPassword(req, resetPasswordRequest));
    }

    @GetMapping("verify-forgot")
    public ResponseEntity<ApiResponse> verifyForgot(@RequestParam("code") String code) {
        return ResponseEntity.ok(userService.verifyConfirmResetPasswordEmail(code));
    }
}
