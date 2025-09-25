package com.ecotur.guajira.backend.Entities.Dto;

import com.ecotur.guajira.backend.Entities.Post;
import com.ecotur.guajira.backend.Entities.PostImage;
import com.ecotur.guajira.backend.Entities.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class PostDTO {
    private String id;
    private String content;
    private PostType type;
    private String authorName;
    private String authorAvatar;
    private List<PostImage> images;
    private Integer likesCount;
    private Integer commentsCount;
    private Boolean isLiked;
    private Boolean isVisible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.type = post.getType();
        this.authorName = post.getAuthorName();
        this.authorAvatar = post.getAuthorAvatar();
        this.images = post.getImages();
        this.likesCount = post.getLikesCount();
        this.commentsCount = post.getCommentsCount();
        this.isLiked = post.getIsLiked();
        this.isVisible = post.getIsVisible();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
