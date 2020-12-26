package com.ismail.forum.service;

import com.ismail.forum.entity.Tag;
import com.ismail.forum.error.NotFoundException;
import com.ismail.forum.helper.Response;
import com.ismail.forum.model.TagRequest;
import com.ismail.forum.model.TagResponse;
import com.ismail.forum.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<TagResponse> getAll() {
        List<Tag> tags = this.tagRepository.getTags();

        List<TagResponse> responses = new ArrayList<TagResponse>();
        tags.stream().forEach(tag -> {
            responses.add(Response.convertTagToResponse(tag));
        });

        return responses;
    }

    public TagResponse createTag(TagRequest tagRequest) {
        Tag tag = new Tag();
        tag.setName(tagRequest.getName());
        return Response.convertTagToResponse(this.tagRepository.saveAndFlush(tag));
    }

    public TagResponse findById(Integer id) {
        return Response.convertTagToResponse(findTagOrNotFound(id));
    }

    public TagResponse updateTag(Integer id, TagRequest tagRequest) {
        Tag tag = findTagOrNotFound(id);
        tag.setName(tagRequest.getName());
        return Response.convertTagToResponse(this.tagRepository.saveAndFlush(tag));
    }

    public void deleteTag(Integer id) {
        Tag tag = findTagOrNotFound(id);
        this.tagRepository.delete(tag);
    }

    private Tag findTagOrNotFound(Integer id) {
        Optional<Tag> tag = this.tagRepository.findById(id);

        if(tag.isEmpty()) {
            throw new NotFoundException();
        }
        return tag.get();
    }
}
