package com.poly.petfoster.service.posts;

import java.util.List;

import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.response.posts.PostHomePageResponse;

public interface PostService {
    List<PostHomePageResponse> buildPostHomePageResponses(List<Posts> posts);
}
