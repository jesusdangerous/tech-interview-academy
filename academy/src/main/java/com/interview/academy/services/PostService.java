package com.interview.academy.services;

import com.interview.academy.domain.CreatePostRequest;
import com.interview.academy.domain.UpdatePostRequest;
import com.interview.academy.domain.entities.Post;
import com.interview.academy.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
}
