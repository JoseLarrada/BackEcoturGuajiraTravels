package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Comment;
import com.ecotur.guajira.backend.Entities.Dto.CommentDTO;
import com.ecotur.guajira.backend.Services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // GET /api/comments/post/{postId} - Obtener comentarios de un post
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable String postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPost(postId);
            List<CommentDTO> commentDTOs = comments.stream()
                    .map(CommentDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(commentDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/comments/{id}/replies - Obtener respuestas de un comentario
    @GetMapping("/{id}/replies")
    public ResponseEntity<List<CommentDTO>> getRepliesByComment(@PathVariable String id) {
        try {
            List<Comment> replies = commentService.getRepliesByComment(id);
            List<CommentDTO> replyDTOs = replies.stream()
                    .map(CommentDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(replyDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/comments/{id} - Obtener comentario por ID
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable String id) {
        try {
            Optional<Comment> comment = commentService.getCommentById(id);

            if (comment.isPresent()) {
                return ResponseEntity.ok(new CommentDTO(comment.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST /api/comments - Crear nuevo comentario
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody Comment comment) {
        try {
            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentDTO(createdComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // POST /api/comments/{id}/reply - Crear respuesta a un comentario
    @PostMapping("/{id}/reply")
    public ResponseEntity<CommentDTO> createReply(@PathVariable String id, @Valid @RequestBody Comment reply) {
        try {
            Comment createdReply = commentService.createReply(id, reply);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentDTO(createdReply));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // PUT /api/comments/{id} - Actualizar comentario
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable String id, @RequestBody String newContent) {
        try {
            Comment updatedComment = commentService.updateComment(id, newContent);
            return ResponseEntity.ok(new CommentDTO(updatedComment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /api/comments/{id} - Eliminar comentario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST /api/comments/{id}/like - Toggle like en comentario
    @PostMapping("/{id}/like")
    public ResponseEntity<CommentDTO> toggleLike(@PathVariable String id) {
        try {
            Comment comment = commentService.toggleLike(id);
            return ResponseEntity.ok(new CommentDTO(comment));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/comments/count/post/{postId} - Contar comentarios de un post
    @GetMapping("/count/post/{postId}")
    public ResponseEntity<Long> countCommentsByPost(@PathVariable String postId) {
        try {
            Long count = commentService.countCommentsByPost(postId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/comments/count/comment/{commentId} - Contar respuestas de un comentario
    @GetMapping("/count/comment/{commentId}")
    public ResponseEntity<Long> countRepliesByComment(@PathVariable String commentId) {
        try {
            Long count = commentService.countRepliesByComment(commentId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/comments/author/{authorName} - Comentarios por autor
    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<CommentDTO>> getCommentsByAuthor(@PathVariable String authorName) {
        try {
            List<Comment> comments = commentService.getCommentsByAuthor(authorName);
            List<CommentDTO> commentDTOs = comments.stream()
                    .map(CommentDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(commentDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
