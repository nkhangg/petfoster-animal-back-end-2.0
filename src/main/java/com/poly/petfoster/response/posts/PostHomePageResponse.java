package com.poly.petfoster.response.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PostHomePageResponse {
    private Integer id;
    private String title;
    private String contents;
    private String thumbnail;
    private boolean containVideo;
}
