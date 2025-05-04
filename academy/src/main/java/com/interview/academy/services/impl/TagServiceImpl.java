package com.interview.academy.services.impl;

import com.interview.academy.domain.entities.Tag;
import com.interview.academy.repositories.TagRepository;
import com.interview.academy.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWIthPostCount();
    }
}
