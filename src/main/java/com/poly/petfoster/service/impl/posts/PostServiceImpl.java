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
import com.poly.petfoster.entity.User;
import com.poly.petfoster.entity.social.Comments;
import com.poly.petfoster.entity.social.Likes;
import com.poly.petfoster.entity.social.Medias;
import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.repository.CommentRepository;
import com.poly.petfoster.repository.LikesRepository;
import com.poly.petfoster.repository.MediasRepository;
import com.poly.petfoster.repository.PostsRepository;
import com.poly.petfoster.repository.UserRepository;
import com.poly.petfoster.request.comments.CommentPostRequest;
import com.poly.petfoster.response.ApiResponse;
import com.poly.petfoster.response.common.PagiantionResponse;
import com.poly.petfoster.response.posts.PostDetailResponse;
import com.poly.petfoster.response.posts.PostMediaResponse;
import com.poly.petfoster.response.posts.PostResponse;
import com.poly.petfoster.service.impl.comments.CommentServiceImpl;
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
        private CommentServiceImpl commentServiceImpl;

        @Autowired
        private JwtProvider jwtProvider;

        @Autowired
        private CommentRepository commentRepository;

        @Autowired
        private MediasRepository mediasRepository;

        @Override
        public List<PostResponse> buildPostHomePageResponses(List<Posts> posts) {
                String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                                .getHeader("Authorization");

                return posts.stream().map(post -> {
                        boolean isLike = false;

                        User user = userServiceImpl.getUserFromToken(token);

                        if (token != null && user != null) {
                                isLike = likesRepository.existByUserAndPost(user.getId(),
                                                post.getId()) != null;
                        }

                        Medias medias = mediasRepository.findByIndex(post, 0);

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
                        User user = userServiceImpl.getUserFromToken(token);

                        if (user != null) {
                                isLike = likesRepository.existByUserAndPost(user.getId(),
                                                post.getId()) != null;
                                owner = user.getUsername().equals(post.getUser().getUsername())
                                                || userServiceImpl.isAdmin(token);
                        }
                }
                List<PostMediaResponse> medias = mediasRepository.findMediasWithPost(post).stream().map(media -> {
                        return PostMediaResponse.builder().url(portUltil.getUrlImage(media.getName()))
                                        .id(media.getId())
                                        .index(media.getIndex())
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

        @Override
        public ApiResponse likePost(String uuid, String token) {
                if (uuid == null) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                if (token == null) {
                        return ApiResponse.builder()
                                        .message("Please login to use")
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .build();
                }

                Posts posts = postsRepository.findByUuid(uuid);
                User user = userServiceImpl.getUserFromToken(token);

                if (posts == null || user == null) {
                        return ApiResponse.builder()
                                        .message("Something not found")
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }
                Likes checkLiked = likesRepository.existByUserAndPost(user.getId(), posts.getId());

                if (checkLiked != null) {
                        likesRepository.delete(checkLiked);

                        return ApiResponse.builder()
                                        .message("Successfuly")
                                        .errors(false)
                                        .status(HttpStatus.OK.value())
                                        .data(buildDetailResponse(posts))
                                        .build();
                }

                Likes like = Likes.builder()
                                .post(posts)
                                .user(user)
                                .build();

                if (like == null) {
                        return ApiResponse.builder()
                                        .message("Failure")
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .build();
                }

                // save like for this post
                likesRepository.save(like);

                return ApiResponse.builder()
                                .message("Successfuly")
                                .errors(false)
                                .status(HttpStatus.OK.value())
                                .data(buildDetailResponse(posts))
                                .build();
        }

        @Override
        public ApiResponse deletePost(String uuid, String token) {
                if (uuid == null) {
                        return ApiResponse.builder()
                                        .message(RespMessage.NOT_FOUND.getValue())
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                if (token == null) {
                        return ApiResponse.builder()
                                        .message("Please login to use")
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .build();
                }

                Posts posts = postsRepository.findByUuid(uuid);
                User user = userServiceImpl.getUserFromToken(token);

                if (posts == null || user == null) {
                        return ApiResponse.builder()
                                        .message("Something not found")
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.NOT_FOUND.value())
                                        .build();
                }

                if (!posts.getUser().getId().equals(user.getId()) && !userServiceImpl.isAdmin(user)) {
                        return ApiResponse.builder()
                                        .message("Unauthorizated")
                                        .data(null)
                                        .errors(true)
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .build();
                }

                postsRepository.delete(posts);

                return ApiResponse.builder()
                                .message("Successfuly")
                                .errors(false)
                                .status(HttpStatus.OK.value())
                                .data(buildDetailResponse(posts))
                                .build();
        }

        @Override
        public ApiResponse createPost() {
                UUID uuid = UUID.randomUUID();

                System.out.println(uuid.toString());

                return ApiResponse.builder()
                                .message("Successfuly")
                                .errors(false)
                                .status(HttpStatus.OK.value())
                                .data(uuid.toString())
                                .build();
        }

}
