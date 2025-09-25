package com.ecotur.guajira.backend.Services;

import com.ecotur.guajira.backend.Entities.Comment;
import com.ecotur.guajira.backend.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    // Obtener comentarios principales de un post
    public List<Comment> getCommentsByPost(String postId) {
        return commentRepository.findByPostIdAndParentIdIsNullOrderByCreatedAtAsc(postId);
    }

    // Obtener respuestas de un comentario
    public List<Comment> getRepliesByComment(String commentId) {
        return commentRepository.findByParentIdOrderByCreatedAtAsc(commentId);
    }

    // Obtener comentario por ID
    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    // Crear nuevo comentario
    public Comment createComment(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setLikesCount(0);
        comment.setIsLiked(false);

        Comment savedComment = commentRepository.save(comment);

        // Incrementar contador de comentarios en el post
        if (comment.getParentId() == null) { // Solo para comentarios principales
            postService.incrementCommentCount(comment.getPostId());
        }

        return savedComment;
    }

    // Crear respuesta a un comentario
    public Comment createReply(String parentCommentId, Comment reply) {
        Optional<Comment> parentOptional = commentRepository.findById(parentCommentId);

        if (parentOptional.isPresent()) {
            Comment parentComment = parentOptional.get();
            reply.setPostId(parentComment.getPostId());
            reply.setParentId(parentCommentId);
            reply.setCreatedAt(LocalDateTime.now());
            reply.setUpdatedAt(LocalDateTime.now());
            reply.setLikesCount(0);
            reply.setIsLiked(false);

            Comment savedReply = commentRepository.save(reply);

            // Agregar la respuesta al comentario padre
            parentComment.addReply(savedReply);
            commentRepository.save(parentComment);

            return savedReply;
        } else {
            throw new RuntimeException("Parent comment not found with id: " + parentCommentId);
        }
    }

    // Actualizar comentario
    public Comment updateComment(String id, String newContent) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(newContent);
            comment.setUpdatedAt(LocalDateTime.now());

            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found with id: " + id);
        }
    }

    // Eliminar comentario
    public void deleteComment(String id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // Si es un comentario principal, decrementar contador del post
            if (comment.getParentId() == null) {
                postService.decrementCommentCount(comment.getPostId());
            } else {
                // Si es una respuesta, removerla del comentario padre
                Optional<Comment> parentOptional = commentRepository.findById(comment.getParentId());
                if (parentOptional.isPresent()) {
                    Comment parentComment = parentOptional.get();
                    parentComment.removeReply(id);
                    commentRepository.save(parentComment);
                }
            }

            // Eliminar el comentario y todas sus respuestas
            commentRepository.deleteByIdOrParentId(id, id);
        } else {
            throw new RuntimeException("Comment not found with id: " + id);
        }
    }

    // Toggle like en comentario
    public Comment toggleLike(String id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            if (comment.getIsLiked()) {
                comment.decrementLikes();
            } else {
                comment.incrementLikes();
            }

            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found with id: " + id);
        }
    }

    // Contar comentarios de un post
    public Long countCommentsByPost(String postId) {
        return commentRepository.countByPostIdAndParentIdIsNull(postId);
    }

    // Contar respuestas de un comentario
    public Long countRepliesByComment(String commentId) {
        return commentRepository.countByParentId(commentId);
    }

    // Obtener comentarios por autor
    public List<Comment> getCommentsByAuthor(String authorName) {
        return commentRepository.findByAuthorNameOrderByCreatedAtDesc(authorName);
    }

    // Eliminar todos los comentarios de un post
    public void deleteCommentsByPost(String postId) {
        commentRepository.deleteByPostId(postId);
    }
}
