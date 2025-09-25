package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
    // Buscar comentarios por post (solo comentarios principales, no respuestas)
    List<Comment> findByPostIdAndParentIdIsNullOrderByCreatedAtAsc(String postId);

    // Buscar respuestas a un comentario específico
    List<Comment> findByParentIdOrderByCreatedAtAsc(String parentId);

    // Buscar todos los comentarios de un post (incluyendo respuestas)
    List<Comment> findByPostIdOrderByCreatedAtAsc(String postId);

    // Contar comentarios principales de un post
    Long countByPostIdAndParentIdIsNull(String postId);

    // Contar respuestas de un comentario
    Long countByParentId(String parentId);

    // Buscar comentarios por autor
    List<Comment> findByAuthorNameOrderByCreatedAtDesc(String authorName);

    // Eliminar todos los comentarios de un post
    void deleteByPostId(String postId);

    // Eliminar comentario y sus respuestas
    void deleteByIdOrParentId(String commentId, String parentId);
}
