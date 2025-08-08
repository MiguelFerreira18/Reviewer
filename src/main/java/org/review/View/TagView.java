package org.review.View;

import org.review.Dto.DisplayTagDTO;
import org.review.Model.Tag;
import org.review.Service.TagService;

import java.util.List;
import java.util.Scanner;

public class TagView {
    private TagService tagService;
    private Scanner sca;

    public TagView(TagService tagService, Scanner scanner) {
        this.tagService = tagService;
        this.sca = scanner;
    }

    public void addTag() {
        System.out.println("Enter tag name:");
        String tagName = sca.nextLine();
        while (tagName.isEmpty()) {
            System.out.println("Tag name cannot be empty. Please enter a valid tag name:");
            tagName = sca.nextLine();
        }

        tagService.addTag(tagName);
    }

    public void deleteTag() {
        System.out.println("Available Tags:");
        printTags();
        System.out.println("Enter tag ID to delete:");
        int tagId = sca.nextInt();
        while (tagId <= 0) {
            System.out.println("Invalid tag ID. Please enter a valid tag ID:");
            tagId = sca.nextInt();
        }
        tagService.deleteTag(tagId);
    }

    private void printTags() {
        tagService.getAllDisplayTags().forEach(tag -> System.out.println("Option: " + tag.id() + ", Name: " + tag.name()));
    }

    public void updateTag() {
        System.out.println("Select tag to update:");
        printTags();
        System.out.println("Enter tag ID to update:");
        int tagId = sca.nextInt();
        while (tagId <= 0) {
            System.out.println("Invalid tag ID. Please enter a valid tag ID:");
            tagId = sca.nextInt();
        }
        sca.nextLine();

        System.out.println("Enter new tag name:");
        String newTagName = sca.nextLine();
        while (newTagName.isEmpty()) {
            System.out.println("Tag name cannot be empty. Please enter a valid tag name:");
            newTagName = sca.nextLine();
        }

        tagService.updateTag(tagId, newTagName);
    }

    public List<DisplayTagDTO> getAllTags() {
        return tagService.getAllDisplayTags();
    }

}
