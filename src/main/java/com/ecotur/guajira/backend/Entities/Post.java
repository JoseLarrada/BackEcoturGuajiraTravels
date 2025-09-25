package com.ecotur.guajira.backend.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    private String id;

    private String content;

    private PostType type; // TEXT, IMAGE, MIXED

    private String authorName;

    private String authorAvatar;

    private List<PostImage> images;

    private Integer likesCount = 0;

    private Integer commentsCount = 0;

    private Boolean isLiked = false;

    private Boolean isVisible = true;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;

    @Field("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    public void incrementLikes() {
        this.likesCount++;
        this.isLiked = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void decrementLikes() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
        this.isLiked = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementComments() {
        this.commentsCount++;
        this.updatedAt = LocalDateTime.now();
    }

    public void decrementComments() {
        if (this.commentsCount > 0) {
            this.commentsCount--;
        }
        this.updatedAt = LocalDateTime.now();
    }
}
