package com.poly.petfoster.controller.posts;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.petfoster.request.posts.PostRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.service.posts.PostService;

@RestController
@RequestMapping("/api/user/posts")
public class PostUserController {

    @Autowired
    private PostService postService;

    @DeleteMapping("{uuid}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("uuid") String uuid,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(postService.deletePost(uuid, token));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createPost(@ModelAttribute PostRequest data,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(postService.createPost(data, token));
    }

}
