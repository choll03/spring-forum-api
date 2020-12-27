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
        tags.forEach(tag -> {
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
        this.tagRepository.updateTagById(id, tagRequest.getName());
        return findById(id);
    }

    public void deleteTag(Integer id) {
        this.tagRepository.deleteTagById(id);
    }

    private Tag findTagOrNotFound(Integer id) {
        Optional<Tag> tag = this.tagRepository.findById(id);

        if(tag.isEmpty()) {
            throw new NotFoundException("Data not Found");
        }
        return tag.get();
    }
}
