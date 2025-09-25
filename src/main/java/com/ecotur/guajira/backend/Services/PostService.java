package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Post;
import com.ecotur.guajira.backend.Entities.PostType;
import com.ecotur.guajira.backend.Repository.CommentRepository;
import com.ecotur.guajira.backend.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    // Obtener todos los posts visibles
    public List<Post> getAllPosts() {
        return postRepository.findByIsVisibleTrueOrderByCreatedAtDesc();
    }

    // Obtener posts con paginación
    public Page<Post> getPostsPaginated(int page, int size, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        switch (sortBy.toLowerCase()) {
            case "popular":
                sort = Sort.by(Sort.Direction.DESC, "likesCount", "createdAt");
                break;
            case "comments":
                sort = Sort.by(Sort.Direction.DESC, "commentsCount", "createdAt");
                break;
            default:
                sort = Sort.by(Sort.Direction.DESC, "createdAt");
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        return postRepository.findByIsVisibleTrue(pageable);
    }

    // Obtener post por ID
    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    // Crear nuevo post
    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setLikesCount(0);
        post.setCommentsCount(0);
        post.setIsLiked(false);
        post.setIsVisible(true);

        return postRepository.save(post);
    }

    // Actualizar post
    public Post updatePost(String id, Post postDetails) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setContent(postDetails.getContent());
            post.setType(postDetails.getType());
            post.setImages(postDetails.getImages());
            post.setUpdatedAt(LocalDateTime.now());

            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with id: " + id);
        }
    }

    // Eliminar post (soft delete)
    public void deletePost(String id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setIsVisible(false);
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);

            // Eliminar comentarios asociados
            commentRepository.deleteByPostId(id);
        } else {
            throw new RuntimeException("Post not found with id: " + id);
        }
    }

    // Eliminar post permanentemente
    public void deletePostPermanently(String id) {
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }

    // Toggle like en post
    public Post toggleLike(String id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (post.getIsLiked()) {
                post.decrementLikes();
            } else {
                post.incrementLikes();
            }

            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with id: " + id);
        }
    }

    // Buscar posts por contenido
    public List<Post> searchPosts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllPosts();
        }
        return postRepository.findByContentContainingIgnoreCaseAndIsVisibleTrue(query.trim());
    }

    // Filtrar posts por tipo
    public List<Post> getPostsByType(PostType type) {
        return postRepository.findByTypeAndIsVisibleTrueOrderByCreatedAtDesc(type);
    }

    // Obtener posts por autor
    public List<Post> getPostsByAuthor(String authorName) {
        return postRepository.findByAuthorNameAndIsVisibleTrueOrderByCreatedAtDesc(authorName);
    }

    // Obtener estadísticas de posts
    public PostStats getPostStats(String authorName) {
        Long totalPosts = postRepository.countByAuthorNameAndIsVisibleTrue(authorName);
        List<Post> posts = getPostsByAuthor(authorName);

        int totalLikes = posts.stream()
                .mapToInt(Post::getLikesCount)
                .sum();

        int totalComments = posts.stream()
                .mapToInt(Post::getCommentsCount)
                .sum();

        double avgInteractions = posts.isEmpty() ? 0 :
                (double) (totalLikes + totalComments) / posts.size();

        return new PostStats(totalPosts, totalLikes, totalComments, avgInteractions);
    }

    // Incrementar contador de comentarios
    public void incrementCommentCount(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.incrementComments();
            postRepository.save(post);
        }
    }

    // Decrementar contador de comentarios
    public void decrementCommentCount(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.decrementComments();
            postRepository.save(post);
        }
    }

    // Clase interna para estadísticas
    public static class PostStats {
        private Long totalPosts;
        private Integer totalLikes;
        private Integer totalComments;
        private Double avgInteractions;

        public PostStats(Long totalPosts, Integer totalLikes, Integer totalComments, Double avgInteractions) {
            this.totalPosts = totalPosts;
            this.totalLikes = totalLikes;
            this.totalComments = totalComments;
            this.avgInteractions = avgInteractions;
        }

        // Getters y setters
        public Long getTotalPosts() { return totalPosts; }
        public void setTotalPosts(Long totalPosts) { this.totalPosts = totalPosts; }

        public Integer getTotalLikes() { return totalLikes; }
        public void setTotalLikes(Integer totalLikes) { this.totalLikes = totalLikes; }

        public Integer getTotalComments() { return totalComments; }
        public void setTotalComments(Integer totalComments) { this.totalComments = totalComments; }

        public Double getAvgInteractions() { return avgInteractions; }
        public void setAvgInteractions(Double avgInteractions) { this.avgInteractions = avgInteractions; }
    }
}
