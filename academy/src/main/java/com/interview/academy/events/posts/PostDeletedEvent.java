package com.interview.academy.events.posts;

import com.interview.academy.domain.entities.Post;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class PostDeletedEvent extends ApplicationEvent {
    private final Post post;

    public PostDeletedEvent(Object source, Post post) {
        super(source);
        this.post = post;
    }
}
