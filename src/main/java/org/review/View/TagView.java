package org.review.View;

import org.review.Model.Tag;
import org.review.Service.TagService;

public class TagView {
    TagService tagService;

    public TagView(TagService tagService) {
        this.tagService = tagService;
    }

    public void addTag(String tagName) {
        tagService.addTag(tagName);
    }

    public void deleteTag(int tagId) {
        tagService.deleteTag(tagId);
    }

    public void updateTag(int tagId, String newTagName) {
        tagService.updateTag(tagId, newTagName);
    }

    public Tag getTagById(int tagId) {
        return tagService.getTagById(tagId);
    }

}
