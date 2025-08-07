package org.review.Service;

import org.review.Database.*;
import org.review.Mapper.TagMapper;
import org.review.Model.Tag;

import java.sql.ResultSet;
import java.util.List;

public class TagService {
    private DatabaseStrategy database;
    private TagMapper tagMapper;

    public TagService(DatabaseStrategy database) {
        this.database = database;
        this.tagMapper = new TagMapper();
    }


    public void addTag(String tagName) {
        CRUDBuilder crudBuilder = new CRUDBuilder();
        crudBuilder.operation(Operation.INSERT).table("tag").values(tagName);
        String query = crudBuilder.build();
        try {
            database.executeQuery(query, Execution.UPDATE);
            System.out.println("Tag added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding tag: " + e.getMessage());
        }
    }

    public void deleteTag(int tagId) {
        CRUDBuilder crudBuilder = new CRUDBuilder();
        crudBuilder.operation(Operation.DELETE).table("tag").where("id = " + tagId);
        String query = crudBuilder.build();
        try {
            database.executeQuery(query, Execution.UPDATE);
            System.out.println("Tag deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting tag: " + e.getMessage());
        }
    }

    public void updateTag(int tagId, String newTagName) {
        CRUDBuilder crudBuilder = new CRUDBuilder();
        crudBuilder.operation(Operation.UPDATE).table("tag")
                .set("tag_name", newTagName)
                .where("id = " + tagId);
        String query = crudBuilder.build();
        try {
            database.executeQuery(query, Execution.UPDATE);
            System.out.println("Tag updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating tag: " + e.getMessage());
        }
    }

    public Tag getTagById(int tagId) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select("id, tag_name")
                .from("tag")
                .where("id = " + tagId);
        String query = queryBuilder.build();
        try {
            ResultSet rs = database.executeQuery(query, Execution.READ);
            return tagMapper.mapToTag(rs);

        } catch (Exception e) {
            System.err.println("Error retrieving tag: " + e.getMessage());
            return null;
        }
    }

    public List<Tag> getAllTags() {
        QueryBuilder queryBuilder = new QueryBuilder()
                .select("id, tag_name")
                .from("tag");
        String query = queryBuilder.build();
        try {
            ResultSet rs = database.executeQuery(query, Execution.READ);
            return tagMapper.mapToTags(rs);
        } catch (Exception e) {
            System.err.println("Error retrieving tags: " + e.getMessage());
            return null;
        }
    }
}
