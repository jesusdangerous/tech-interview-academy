package com.interview.academy.events.tags;

import com.interview.academy.domain.entities.Tag;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class TagsCreatedEvent extends ApplicationEvent {

    List<Tag> tags;

    public TagsCreatedEvent(Object source, List<Tag> tags) {
        super(source);
        this.tags = tags;
    }
}
