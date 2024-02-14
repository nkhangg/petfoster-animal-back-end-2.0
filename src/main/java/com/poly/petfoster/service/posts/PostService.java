package com.poly.petfoster.service.posts;

import java.util.List;
import java.util.Optional;

import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.posts.PostResponse;

public interface PostService {
    List<PostResponse> buildPostHomePageResponses(List<Posts> posts);

    ApiResponse hightlight();

    ApiResponse hightlightOfUser(String username);

    ApiResponse posts(Optional<String> search, Optional<Integer> page);

    ApiResponse postsOfUser(String username, Optional<Integer> page, Optional<String> type);

    ApiResponse detailPost(String uuid);
}
