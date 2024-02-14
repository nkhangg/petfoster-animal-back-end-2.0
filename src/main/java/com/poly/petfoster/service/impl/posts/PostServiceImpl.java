package com.poly.petfoster.service.impl.posts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.poly.petfoster.config.JwtProvider;
import com.poly.petfoster.constant.RespMessage;
import com.poly.petfoster.entity.Feedback;
import com.poly.petfoster.entity.User;
import com.poly.petfoster.entity.social.Medias;
import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.repository.LikesRepository;
import com.poly.petfoster.repository.PostsRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.posts.PostDetailResponse;
import com.poly.petfoster.response.posts.PostMediaResponse;
import com.poly.petfoster.response.posts.PostResponse;
import com.poly.petfoster.service.impl.user.UserServiceImpl;
import com.poly.petfoster.service.posts.PostService;
import com.poly.petfoster.service.user.UserService;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class PostServiceImpl implements PostService {

        @Autowired
        private PortUltil portUltil;

        @Autowired
        private PostsRepository postsRepository;

        @Autowired
        private UserServiceImpl userServiceImpl;

        @Autowired
        private LikesRepository likesRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private JwtProvider jwtProvider;

        @Override
        public List<PostResponse> buildPostHomePageResponses(List<Posts> posts) {
                String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                                .getHeader("Authorization");

                return posts.stream().map(post -> {
                        boolean isLike = false;

                        if (token != null) {
                                isLike = likesRepository.existByUserAndPost(post.getUser().getId(),
                                                post.getId()) != null;
                        }
                        Medias medias = post.getMedias().get(0);

                        return PostResponse.builder()
                                        .id(post.getUuid())
                                        .title(post.getTitle())
                                        .thumbnail(portUltil.getUrlImage(medias.getName()))
                                        .containVideo(medias.getIsVideo())
                                        .comments(post.getComments().size())
                                        .likes(post.getLikes().size())
                                        .isLike(isLike)
                                        .user(userServiceImpl.buildUserProfileResponse(post.getUser()))
                                        .build();
                }).toList();
        }

        public PostDetailResponse buildDetailResponse(Posts post) {
                String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                                .getHeader("Authorization");

                boolean isLike = false;
                boolean owner = false;

                if (token != null) {
                        isLike = likesRepository.existByUserAndPost(post.getUser().getId(),
                                        post.getId()) != null;

                        String username = jwtProvider.getUsernameFromToken(token);
                        if (username != null) {
                                owner = username.equals(post.getUser().getUsername());
                        }
                }
                List<PostMediaResponse> medias = post.getMedias().stream().map(media -> {
                        return PostMediaResponse.builder().url(portUltil.getUrlImage(media.getName()))
                                        .isVideo(media.getIsVideo()).build();
                }).toList();

                return PostDetailResponse.builder()
                                .id(post.getUuid())
                                .title(post.getTitle())
                                .comments(post.getComments().size())
                                .likes(post.getLikes().size())
                                .isLike(isLike)
                                .user(userServiceImpl.buildUserProfileResponse(post.getUser()))
                                .images(medias)
                                .owner(owner)
                                .createdAt(post.getCreateAt())
                                .build();

        }

        @Override
        public ApiResponse hightlight() {

                List<Posts> posts = postsRepository.hightlight();

                if (posts == null || posts.isEmpty()) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(new ArrayList<>())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                return ApiResponse.builder()
                                .message("Succeessfuly")
                                .status(HttpStatus.OK.value())
                                .errors(false)
                                .data(buildPostHomePageResponses(posts))
                                .build();
        }

        @Override
        public ApiResponse posts(Optional<String> search, Optional<Integer> page) {

                List<Posts> posts = postsRepository.posts(search);

                if (posts == null || posts.isEmpty()) {
                        return ApiResponse.builder()
                                        .message("Failure")
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .errors(true)
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .build();
                }

                Pageable pageable = PageRequest.of(page.orElse(0), 10);
                int startIndex = (int) pageable.getOffset();
                int endIndex = Math.min(startIndex + pageable.getPageSize(), posts.size());

                if (startIndex >= endIndex) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                List<Posts> visiblePosts = posts.subList(startIndex, endIndex);

                if (visiblePosts == null) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                Page<Posts> pagination = new PageImpl<Posts>(visiblePosts, pageable,
                                posts.size());

                return ApiResponse.builder()
                                .message("Succeessfuly")
                                .status(HttpStatus.OK.value())
                                .errors(false)
                                .data(PagiantionResponse.builder().data(buildPostHomePageResponses(visiblePosts))
                                                .pages(pagination.getTotalPages()).build())
                                .build();
        }

        @Override
        public ApiResponse hightlightOfUser(String username) {
                List<Posts> posts = postsRepository.hightlightOfUser(username);

                if (posts == null || posts.isEmpty()) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(new ArrayList<>())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                return ApiResponse.builder()
                                .message("Succeessfuly")
                                .status(HttpStatus.OK.value())
                                .errors(false)
                                .data(buildPostHomePageResponses(posts))
                                .build();
        }

        public List<Posts> getListPostsOfUser(String username, Optional<String> rawtype) {
                String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                                .getHeader("Authorization");

                String type = rawtype.orElse(null);

                if (type != null && type.equals("likes")) {
                        if (token != null) {
                                String usernameFromToken = jwtProvider.getUsernameFromToken(token);

                                if (usernameFromToken.equals(username)) {
                                        return postsRepository.postsLikeOfUser(username);
                                }
                        }
                        return new ArrayList<>();
                }
                if (type != null && type.equals("posts")) {
                        return postsRepository.postsOfUser(username);
                }

                return postsRepository.postsOfUser(username);

        }

        @Override
        public ApiResponse postsOfUser(String username, Optional<Integer> page, Optional<String> type) {
                List<Posts> posts = this.getListPostsOfUser(username, type);

                if (posts == null || posts.isEmpty()) {
                        return ApiResponse.builder()
                                        .message("Failure")
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .errors(true)
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .build();
                }

                Pageable pageable = PageRequest.of(page.orElse(0), 10);
                int startIndex = (int) pageable.getOffset();
                int endIndex = Math.min(startIndex + pageable.getPageSize(), posts.size());

                if (startIndex >= endIndex) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                List<Posts> visiblePosts = posts.subList(startIndex, endIndex);

                if (visiblePosts == null) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(PagiantionResponse.builder().data(new ArrayList<>()).pages(0).build())
                                        .errors(false)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                Page<Posts> pagination = new PageImpl<Posts>(visiblePosts, pageable,
                                posts.size());

                return ApiResponse.builder()
                                .message("Succeessfuly")
                                .status(HttpStatus.OK.value())
                                .errors(false)
                                .data(PagiantionResponse.builder().data(buildPostHomePageResponses(visiblePosts))
                                                .pages(pagination.getTotalPages()).build())
                                .build();
        }

        @Override
        public ApiResponse detailPost(String uuid) {

                Posts posts = postsRepository.findByUuid(uuid);

                if (posts == null) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                return ApiResponse.builder()
                                .message("Succeessfuly")
                                .status(HttpStatus.OK.value())
                                .errors(false)
                                .data(buildDetailResponse(posts))
                                .build();
        }

}
