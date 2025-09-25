package com.ecotur.guajira.backend.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    private String id;

    private String postId;

    private String parentId; // Para respuestas a comentarios

    private String content;

    private String authorName;

    private String authorAvatar;

    private Integer likesCount = 0;

    private Boolean isLiked = false;

    private List<Comment> replies = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedAt;

    // Métodos de utilidad
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

    public void addReply(Comment reply) {
        this.replies.add(reply);
        this.updatedAt = LocalDateTime.now();
    }

    public void removeReply(String replyId) {
        this.replies.removeIf(reply -> reply.getId().equals(replyId));
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isReply() {
        return this.parentId != null && !this.parentId.isEmpty();
    }
}
