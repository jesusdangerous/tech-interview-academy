package com.interview.academy.services;

import com.interview.academy.domain.entities.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<Tag> getTags();
    List<Tag> createTags(Set<String> tagNames);
}
