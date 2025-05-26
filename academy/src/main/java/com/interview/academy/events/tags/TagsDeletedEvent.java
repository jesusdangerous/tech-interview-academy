package com.interview.academy.events.tags;

import com.interview.academy.domain.entities.Tag;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class TagsDeletedEvent extends ApplicationEvent {
    List<Tag> tags;

    public TagsDeletedEvent(Object source) {
        super(source);
    }
}
