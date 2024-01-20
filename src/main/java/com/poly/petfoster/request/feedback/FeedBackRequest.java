package com.poly.petfoster.request.feedback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeedBackRequest {
    private String fullname;
    private String phone;
    private String email;
    private String message;
}
