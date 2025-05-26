package com.interview.academy.controllers;

import com.interview.academy.domain.CreatePostRequest;
import com.interview.academy.domain.UpdatePostRequest;
import com.interview.academy.domain.dtos.CreatePostRequestDto;
import com.interview.academy.domain.dtos.PostDto;
import com.interview.academy.domain.dtos.UpdatePostRequestDto;
import com.interview.academy.domain.entities.Post;
import com.interview.academy.domain.entities.User;
import com.interview.academy.exceptions.ForbiddenOperationException;
import com.interview.academy.mappers.PostMapper;
import com.interview.academy.services.PostService;
import com.interview.academy.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private PostMapper postMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostController postController;

    private UUID userId;
    private User user;
    private UUID postId;
    private Post post;
    private PostDto postDto;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        postId = UUID.randomUUID();

        user = new User();
        user.setId(userId);

        post = new Post();
        post.setId(postId);
        post.setAuthor(user);

        postDto = new PostDto();
        postDto.setId(postId);
    }

    @Test
    void testGetAllPostsReturnsListOfPostDtos() {
        when(postService.getAllPosts(null, null)).thenReturn(List.of(post));
        when(postMapper.toDto(post)).thenReturn(postDto);

        ResponseEntity<List<PostDto>> response = postController.getAllPosts(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(postDto, response.getBody().getFirst());

        verify(postService).getAllPosts(null, null);
        verify(postMapper).toDto(post);
    }

    @Test
    void testGetDraftsReturnsListOfDraftPostDtos() {
        when(userService.getUserById(userId)).thenReturn(user);
        when(postService.getDraftPosts(user)).thenReturn(List.of(post));
        when(postMapper.toDto(post)).thenReturn(postDto);

        ResponseEntity<List<PostDto>> response = postController.getDrafts(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(postDto, response.getBody().getFirst());

        verify(userService).getUserById(userId);
        verify(postService).getDraftPosts(user);
        verify(postMapper).toDto(post);
    }

    @Test
    void testCreateDtoCreatesPostAndReturnsCreatedDto() {
        CreatePostRequestDto createDto = new CreatePostRequestDto();
        CreatePostRequest createRequest = new CreatePostRequest();

        when(userService.getUserById(userId)).thenReturn(user);
        when(postMapper.toCreatePostRequest(createDto)).thenReturn(createRequest);
        when(postService.createPost(user, createRequest)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postDto);

        ResponseEntity<PostDto> response = postController.createDto(createDto, userId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(postDto, response.getBody());

        verify(userService).getUserById(userId);
        verify(postMapper).toCreatePostRequest(createDto);
        verify(postService).createPost(user, createRequest);
        verify(postMapper).toDto(post);
    }

    @Test
    void testUpdatePostUpdatesPostWhenUserIsAuthor() {
        UpdatePostRequestDto updateDto = new UpdatePostRequestDto();
        UpdatePostRequest updateRequest = new UpdatePostRequest();

        when(userService.getUserById(userId)).thenReturn(user);
        when(postService.getPost(postId)).thenReturn(post);
        when(postMapper.toUpdatePostRequest(updateDto)).thenReturn(updateRequest);
        when(postService.updatePost(postId, updateRequest)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postDto);

        ResponseEntity<PostDto> response = postController.updatePost(postId, updateDto, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDto, response.getBody());

        verify(userService).getUserById(userId);
        verify(postService).getPost(postId);
        verify(postMapper).toUpdatePostRequest(updateDto);
        verify(postService).updatePost(postId, updateRequest);
        verify(postMapper).toDto(post);
    }

    @Test
    void testUpdatePostThrowsForbiddenOperationExceptionWhenUserIsNotAuthor() {
        User otherUser = new User();
        otherUser.setId(UUID.randomUUID());
        post.setAuthor(otherUser);

        when(userService.getUserById(userId)).thenReturn(user);
        when(postService.getPost(postId)).thenReturn(post);

        ForbiddenOperationException ex = assertThrows(ForbiddenOperationException.class,
                () -> postController.updatePost(postId, new UpdatePostRequestDto(), userId));
        assertEquals("You are not allowed to update this post", ex.getMessage());

        verify(userService).getUserById(userId);
        verify(postService).getPost(postId);
        verifyNoMoreInteractions(postMapper, postService);
    }

    @Test
    void testGetPostReturnsPostDto() {
        when(postService.getPost(postId)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postDto);

        ResponseEntity<PostDto> response = postController.getPost(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDto, response.getBody());

        verify(postService).getPost(postId);
        verify(postMapper).toDto(post);
    }

    @Test
    void testDeletePostDeletesPostWhenUserIsAuthor() {
        when(userService.getUserById(userId)).thenReturn(user);
        when(postService.getPost(postId)).thenReturn(post);

        ResponseEntity<Void> response = postController.deletePost(postId, userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(postService).deletePost(postId);
    }

    @Test
    void testDeletePostThrowsForbiddenOperationExceptionWhenUserIsNotAuthor() {
        User otherUser = new User();
        otherUser.setId(UUID.randomUUID());
        post.setAuthor(otherUser);

        when(userService.getUserById(userId)).thenReturn(user);
        when(postService.getPost(postId)).thenReturn(post);

        ForbiddenOperationException ex = assertThrows(ForbiddenOperationException.class,
                () -> postController.deletePost(postId, userId));
        assertEquals("You are not allowed to delete this post", ex.getMessage());

        verify(userService).getUserById(userId);
        verify(postService).getPost(postId);
        verify(postService, never()).deletePost(postId);
    }
}
