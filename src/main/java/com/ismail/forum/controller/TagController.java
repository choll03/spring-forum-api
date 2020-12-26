package com.ismail.forum.controller;

import com.ismail.forum.model.TagRequest;
import com.ismail.forum.model.TagResponse;
import com.ismail.forum.model.WebResponse;
import com.ismail.forum.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public WebResponse<List<TagResponse>> getAllTags() {
        return new WebResponse<List<TagResponse>>(
                200,
                "ok",
                this.tagService.getAll()
        );
    }

    @GetMapping("/{id}")
    public WebResponse<TagResponse> findTagById(@PathVariable("id") Integer id) {
        return new WebResponse<TagResponse>(
                200,
                "ok",
                this.tagService.findById(id)
        );
    }

    @PostMapping
    public WebResponse<TagResponse> createTag(@RequestBody TagRequest tagRequest) {
        return new WebResponse<TagResponse>(
                201,
                "ok",
                this.tagService.createTag(tagRequest)
        );
    }

    @PutMapping("/{id}")
    public WebResponse<TagResponse> updateTag(@PathVariable Integer id, @RequestBody TagRequest tagRequest) {
        return new WebResponse<TagResponse>(
                200,
                "ok",
                this.tagService.updateTag(id, tagRequest)
        );
    }

    @DeleteMapping("/{id}")
    public WebResponse<String> deleteTag(@PathVariable Integer id) {
        this.tagService.deleteTag(id);
        return new WebResponse<String>(
                200,
                "ok",
                null
        );

    }

}
