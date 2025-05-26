package com.interview.academy.listeners;

import com.interview.academy.events.PostCreatedEvent;
import com.interview.academy.events.PostDeletedEvent;
import com.interview.academy.events.PostUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostEventListener {

    @Async
    @EventListener
    public void handlePostCreated(PostCreatedEvent event) {
        log.info("New post created: ID = {}, title = '{}'", event.postId(), event.title());
    }

    @Async
    @EventListener
    public void handlePostDeleted(PostDeletedEvent event) {
        log.info("Post deleted: ID = {}, title = '{}'", event.postId(), event.title());
    }

    @Async
    @EventListener
    public void handlePostUpdated(PostUpdatedEvent event) {
        log.info("Post updated: ID = {}, title = '{}'", event.postId(), event.title());
    }
}
