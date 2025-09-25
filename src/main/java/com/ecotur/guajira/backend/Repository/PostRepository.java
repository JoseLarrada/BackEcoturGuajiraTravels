package com.ecotur.guajira.backend.Repository;

import com.ecotur.guajira.backend.Entities.Post;
import com.ecotur.guajira.backend.Entities.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    // Buscar posts visibles ordenados por fecha
    List<Post> findByIsVisibleTrueOrderByCreatedAtDesc();

    // Buscar posts visibles con paginación
    Page<Post> findByIsVisibleTrue(Pageable pageable);

    // Buscar posts por tipo
    List<Post> findByTypeAndIsVisibleTrueOrderByCreatedAtDesc(PostType type);

    // Buscar posts que contengan texto en el contenido
    @Query("{'content': {$regex: ?0, $options: 'i'}, 'isVisible': true}")
    List<Post> findByContentContainingIgnoreCaseAndIsVisibleTrue(String content);

    // Buscar posts por autor
    List<Post> findByAuthorNameAndIsVisibleTrueOrderByCreatedAtDesc(String authorName);

    // Contar posts por autor
    Long countByAuthorNameAndIsVisibleTrue(String authorName);

    // Posts más populares (por likes) con paginación
    @Query("{'isVisible': true}")
    Page<Post> findByIsVisibleTrueOrderByLikesCountDescCreatedAtDesc(Pageable pageable);

    // Posts más comentados con paginación
    @Query("{'isVisible': true}")
    Page<Post> findByIsVisibleTrueOrderByCommentsCountDescCreatedAtDesc(Pageable pageable);

    // Posts recientes con paginación
    @Query("{'isVisible': true}")
    Page<Post> findByIsVisibleTrueOrderByCreatedAtDesc(Pageable pageable);
}
