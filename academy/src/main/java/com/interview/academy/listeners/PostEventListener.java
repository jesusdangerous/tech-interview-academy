package com.interview.academy.listeners;

import com.interview.academy.events.posts.PostCreatedEvent;
import com.interview.academy.events.posts.PostDeletedEvent;
import com.interview.academy.events.posts.PostUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostEventListener {

    @EventListener
    @Async
    public void handlePostCreated(PostCreatedEvent event) {
        try {
            log.info("New post created: ID = {}, title = '{}'", event.getPost().getId(), event.getPost().getTitle());
        } catch (Exception e) {
            log.error("Error in handlePostCreated", e);
        }
    }

    @EventListener
    @Async
    public void handlePostDeleted(PostDeletedEvent event) {
        try {
            log.info("Post deleted: ID = {}, title = '{}'", event.getPost().getId(), event.getPost().getTitle());
        } catch (Exception e) {
            log.error("Error in handlePostDeleted", e);
        }
    }

    @EventListener
    @Async
    public void handlePostUpdated(PostUpdatedEvent event) {
        try {
            log.info("Post updated: ID = {}, title = '{}'", event.getPost().getId(), event.getPost().getTitle());
        } catch (Exception e) {
            log.error("Error in handlePostUpdated", e);
        }
    }
}
