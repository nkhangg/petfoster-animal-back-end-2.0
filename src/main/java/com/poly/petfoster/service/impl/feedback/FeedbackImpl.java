package com.poly.petfoster.service.impl.feedback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.poly.petfoster.request.feedback.FeedBackRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.feedback.FeedBackResponse;
import com.poly.petfoster.service.feedback.FeedbackService;
import com.poly.petfoster.ultils.MailUtils;

@Service
public class FeedbackImpl implements FeedbackService {
    @Autowired
    MailUtils mailUtils;

    @Override
    public ApiResponse feedback(HttpServletRequest request, FeedBackRequest feedBackRequest) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("fullname", feedBackRequest.getFullname());
            map.put("email", feedBackRequest.getEmail());
            map.put("phone", feedBackRequest.getPhone());
            map.put("message", feedBackRequest.getMessage());
            map.put("browser", request.getHeader("USER-AGENT"));
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            mailUtils.sendTemplateEmail(feedBackRequest.getEmail(),
                    "Visitor feedback - " + formatter.format(new Date()),
                    "feedback", map);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ApiResponse.builder().message("Successfully").status(HttpStatus.OK.value()).errors(Boolean.FALSE)
                .data(new FeedBackResponse(feedBackRequest.getFullname(),
                        feedBackRequest.getEmail(), feedBackRequest.getMessage()))
                .build();
    }
}
