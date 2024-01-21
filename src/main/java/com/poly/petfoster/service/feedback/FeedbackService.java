package com.poly.petfoster.service.feedback;

import javax.servlet.http.HttpServletRequest;

import com.poly.petfoster.request.feedback.FeedBackRequest;
import com.poly.petfoster.response.ApiResponse;

public interface FeedbackService {
    public ApiResponse getFeedback();

    public ApiResponse feedback(HttpServletRequest request, FeedBackRequest feedBackRequest);

    public ApiResponse seen(Integer id);
}
