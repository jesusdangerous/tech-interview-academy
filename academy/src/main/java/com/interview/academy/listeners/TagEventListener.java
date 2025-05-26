package com.interview.academy.listeners;

import com.interview.academy.events.tags.TagsCreatedEvent;
import com.interview.academy.events.tags.TagsDeletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TagEventListener {
    @EventListener
    @Async
    public void handleTagsCreated(TagsCreatedEvent tags) {
        try {
            log.info("New tags created: {}", tags);
        } catch (Exception e) {
            log.error("Error processing tags creation event", e);
        }
    }

    @EventListener
    @Async
    public void handleTagsDeleted(TagsDeletedEvent tags) {
        try {
            log.info("Tags deleted");
        } catch (Exception e) {
            log.error("Error processing tags update event", e);
        }
    }
}
