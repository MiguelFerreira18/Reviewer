package org.review.Mapper;

import org.review.Dto.DisplayTagDTO;
import org.review.Logger.Logger;
import org.review.Model.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TagMapper {

    public Tag mapToTag(ResultSet tag) {
        try {
            int id = tag.getInt("id");
            String name = tag.getString("tag_name");
            Timestamp createdAt = tag.getTimestamp("created_at");
            Timestamp updatedAt = tag.getTimestamp("updated_at");

            return new Tag(id, name, createdAt.toLocalDateTime(), updatedAt.toLocalDateTime());
        } catch (Exception e) {
            Logger.getInstance().error("Error mapping ResultSet to Tag: " + e.getMessage());
            return null;
        }

    }

    public List<Tag> mapToTags(ResultSet tags) {
        List<Tag> tagList = new ArrayList<>();
        try {
            while (tags.next()) {
                Tag tag = mapToTag(tags);
                if (tag != null) {
                    tagList.add(tag);
                }
            }
        } catch (SQLException e) {
            Logger.getInstance().error("Error mapping ResultSet to List<Tag>: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return tagList;
    }

    public DisplayTagDTO mapToDisplayTagDTO(ResultSet tag) {
        try {
            int id = tag.getInt("id");
            String name = tag.getString("tag_name");

            return new DisplayTagDTO(id, name);
        } catch (SQLException e) {
            Logger.getInstance().error("Error mapping ResultSet to DisplayTagDTO: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public List<DisplayTagDTO> mapToDisplayTagDTOs(ResultSet tags) {
        List<DisplayTagDTO> tagList = new ArrayList<>();
        try {
            while (tags.next()) {
                DisplayTagDTO tag = mapToDisplayTagDTO(tags);
                if (tag != null) {
                    tagList.add(tag);
                }
            }
        } catch (SQLException e) {
            Logger.getInstance().error("Error mapping ResultSet to List<DisplayTagDTO>: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return tagList;
    }


}
