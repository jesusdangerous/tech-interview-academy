package com.interview.academy.services;

import com.interview.academy.domain.entities.Post;
import com.interview.academy.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
}
