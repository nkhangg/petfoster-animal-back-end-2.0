package com.poly.petfoster.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.service.EmailService;
import com.poly.petfoster.ultils.MailUtils;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    MailUtils mailUtils;

    @Override
    public void sendVerificationEmail(HttpServletRequest req ,String email, UUID token) {
        
        String verificationLink = Constant.BASE_URL + "verify?code=" + token;
        String body = "Please click the following link to verify your email: " + verificationLink; 

        mailUtils.sendEmail(email, "Email Verification", body);

    }

}
