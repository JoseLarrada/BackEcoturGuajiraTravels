package com.ecotur.guajira.backend.Entities.Dto;

import com.ecotur.guajira.backend.Entities.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentDTO {
    private String id;
    private String postId;
    private String parentId;
    private String content;
    private String authorName;
    private String authorAvatar;
    private Integer likesCount;
    private Boolean isLiked;
    private List<CommentDTO> replies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor desde entidad
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.parentId = comment.getParentId();
        this.content = comment.getContent();
        this.authorName = comment.getAuthorName();
        this.authorAvatar = comment.getAuthorAvatar();
        this.likesCount = comment.getLikesCount();
        this.isLiked = comment.getIsLiked();
        this.replies = comment.getReplies().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
