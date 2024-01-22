package com.poly.petfoster.controller.admin.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.feedback.FeedbackService;

@RestController
@RequestMapping("/api/admin/feedback")
public class FeedbacksController {
    @Autowired
    FeedbackService feedbackService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getFeedback() {
        return ResponseEntity.ok(feedbackService.getFeedback());
    }

    @GetMapping("/seen/{id}")
    public ResponseEntity<ApiResponse> seen(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(feedbackService.seen(id));
    }
}
