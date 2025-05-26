package com.interview.academy.controllers;

import com.interview.academy.exceptions.ForbiddenOperationException;
import com.interview.academy.domain.CreatePostRequest;
import com.interview.academy.domain.UpdatePostRequest;
import com.interview.academy.domain.dtos.CreatePostRequestDto;
import com.interview.academy.domain.dtos.PostDto;
import com.interview.academy.domain.dtos.UpdatePostRequestDto;
import com.interview.academy.domain.entities.Post;
import com.interview.academy.domain.entities.User;
import com.interview.academy.mappers.PostMapper;
import com.interview.academy.services.PostService;
import com.interview.academy.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        List<Post> draftPosts = postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createDto(
            @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);

        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

//    @PutMapping(path = "/{id}")
//    public ResponseEntity<PostDto> updatePost(
//            @PathVariable UUID id,
//            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {
//        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
//        Post updatedPost = postService.updatePost(id, updatePostRequest);
//        PostDto updatedPostDto = postMapper.toDto(updatedPost);
//
//        return ResponseEntity.ok(updatedPostDto);
//    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto,
            @RequestAttribute UUID userId) {

        User loggedInUser = userService.getUserById(userId);
        Post existingPost = postService.getPost(id);

        if (!existingPost.getAuthor().getId().equals(loggedInUser.getId())) {
            throw new ForbiddenOperationException("You are not allowed to update this post");
        }

        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id, updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatedPost);

        return ResponseEntity.ok(updatedPostDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {
        Post post = postService.getPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
//        postService.deletePost(id);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable UUID id,
            @RequestAttribute UUID userId) {

        User loggedInUser = userService.getUserById(userId);
        Post post = postService.getPost(id);

        if (!post.getAuthor().getId().equals(loggedInUser.getId())) {
            throw new ForbiddenOperationException("You are not allowed to delete this post");
        }

        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
