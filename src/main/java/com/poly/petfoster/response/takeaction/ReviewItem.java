package com.poly.petfoster.response.takeaction;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewItem {
    
    private Integer id;

    private String avatar;

    private String name;

    private Integer rating;

    private List<Integer> sizes;

    private String comment;

    private String createAt;

}
