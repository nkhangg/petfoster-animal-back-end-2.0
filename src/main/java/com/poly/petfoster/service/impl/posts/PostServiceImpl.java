package com.poly.petfoster.service.impl.posts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.petfoster.entity.social.Medias;
import com.poly.petfoster.entity.social.Posts;
import com.poly.petfoster.response.posts.PostHomePageResponse;
import com.poly.petfoster.service.posts.PostService;
import com.poly.petfoster.ultils.PortUltil;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PortUltil portUltil;

    @Override
    public List<PostHomePageResponse> buildPostHomePageResponses(List<Posts> posts) {

        return posts.stream().map(post -> {

            Medias medias = post.getMedias().get(0);

            return PostHomePageResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .contents(post.getTitle())
                    .thumbnail(portUltil.getUrlImage(medias.getName()))
                    .containVideo(medias.getIsVideo())
                    .build();
        }).toList();
    }

}
