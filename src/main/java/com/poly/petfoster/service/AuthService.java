package com.poly.petfoster.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.poly.petfoster.request.LoginRequest;
import com.poly.petfoster.request.RegisterRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.AuthResponse;

public interface AuthService {
    
    AuthResponse login(LoginRequest loginReq);
    AuthResponse register(HttpServletRequest httpServletRequest, RegisterRequest registerReq);
    Authentication authenticate(String username, String password);
    ApiResponse verifyEmail(String token);
    ApiResponse refreshCode(HttpServletRequest httpServletRequest, String oldCode);

}
