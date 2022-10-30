package com.posts.boardPost.service;

import com.posts.boardPost.domain.posts.PostRepository;
import com.posts.boardPost.domain.posts.Posts;
import com.posts.boardPost.web.dto.PostsListResponseDto;
import com.posts.boardPost.web.dto.PostsResponseDto;
import com.posts.boardPost.web.dto.PostsSaveRequestDto;
import com.posts.boardPost.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
       return  postRepository.findAllDesc().stream()
               .map(PostsListResponseDto::new)
               .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postRepository.delete(posts);
    }
}