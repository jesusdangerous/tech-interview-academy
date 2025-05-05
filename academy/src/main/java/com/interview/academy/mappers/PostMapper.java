package com.interview.academy.mappers;

import com.interview.academy.domain.CreatePostRequest;
import com.interview.academy.domain.UpdatePostRequest;
import com.interview.academy.domain.dtos.CreatePostRequestDto;
import com.interview.academy.domain.dtos.PostDto;
import com.interview.academy.domain.dtos.UpdatePostRequestDto;
import com.interview.academy.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "status", source = "status")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
