package com.poly.petfoster.service.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.poly.petfoster.constant.Constant;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.service.EmailService;
import com.poly.petfoster.service.impl.user.UserServiceImpl;
import com.poly.petfoster.ultils.MailUtils;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    MailUtils mailUtils;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    UserRepository userRepository;

    @Override
    public void sendVerificationEmail(HttpServletRequest req, String email, UUID token) {
        String verificationLink = Constant.BASE_URL + "verify?code=" + token;
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("PetFoster - Acctive Your Account!");
            Context context = new Context();
            context.setVariable("action_url", verificationLink);
            context.setVariable("name", req.getAttribute("username"));
            String body = templateEngine.process("active", context);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendConfirmationEmail(HttpServletRequest req, String email, UUID token) {
        // create link
        String verificationLink = Constant.CLIENT_BASE_URL + "reset-password?code=" + token;
        // String body = "Please click the following link to verify reset your password:
        // " + verificationLink;
        User u = userRepository.findByEmail(email).orElse(null);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("PetFoster - Reset Password Confirm!");
            Context context = new Context();
            context.setVariable("action_url", verificationLink);
            context.setVariable("name", u.getDisplayName());
            context.setVariable("browser_name", req.getHeader("USER-AGENT"));
            String body = templateEngine.process("content", context);
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // send link to email

    }

}
