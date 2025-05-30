package com.interview.academy.services.impl;

import com.interview.academy.domain.CreatePostRequest;
import com.interview.academy.domain.PostStatus;
import com.interview.academy.domain.UpdatePostRequest;
import com.interview.academy.domain.dtos.PostEventDto;
import com.interview.academy.domain.entities.*;
import com.interview.academy.events.posts.PostCreatedEvent;
import com.interview.academy.events.posts.PostDeletedEvent;
import com.interview.academy.events.posts.PostUpdatedEvent;
import com.interview.academy.repositories.PostRepository;
import com.interview.academy.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final KafkaProducerService kafkaProducerService;
    private final ApplicationEventPublisher eventPublisher;
    private static final int WORDS_PER_MINUTES = 200;


    @Override
    public Post getPost(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post does not exist with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        Post savedPost = postRepository.save(newPost);

        if (savedPost.getStatus().equals(PostStatus.PUBLISHED)) {
            PostEventDto event = new PostEventDto(
                    savedPost.getTitle(),
                    savedPost.getId()
            );
            kafkaProducerService.sendNewPostEvent(event);
        }

        eventPublisher.publishEvent(new PostCreatedEvent(this, savedPost));
        return savedPost;
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post does not exist with id: " + id));

        existingPost.setTitle(updatePostRequest.getTitle());
        existingPost.setContent(updatePostRequest.getContent());
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));

        UUID categoryId = updatePostRequest.getCategoryId();
        if (!existingPost.getCategory().getId().equals(categoryId)) {
            Category newCategory = categoryService.getCategoryById(categoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();
        if (!existingTagIds.equals(updatePostRequestTagIds)) {
            List<Tag> newTags = tagService.getTagByIds(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(newTags));
        }

        Post savedPost = postRepository.save(existingPost);

        if (savedPost.getStatus().equals(PostStatus.PUBLISHED)) {
            PostEventDto event = new PostEventDto(
                    savedPost.getTitle(),
                    savedPost.getId()
            );
            kafkaProducerService.sendNewPostEvent(event);
        }

        eventPublisher.publishEvent(new PostUpdatedEvent(this, savedPost));
        return savedPost;
    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        eventPublisher.publishEvent(new PostDeletedEvent(this, post));
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        int wordCount = content.trim().split("\\s+").length;

        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTES);
    }
}
