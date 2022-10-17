package com.posts.boardPost.service;

import com.posts.boardPost.domain.posts.PostRepository;
import com.posts.boardPost.domain.posts.Posts;
import com.posts.boardPost.domain.posts.PostsSaveRequestDto;
import com.posts.boardPost.web.dto.PostsResponseDto;
import com.posts.boardPost.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }
    public PostsResponseDto findById (Long id) {
        Posts entity = postRepository.findById(id).orElseThrow(()
        -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }
}