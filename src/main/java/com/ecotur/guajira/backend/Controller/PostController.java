package com.ecotur.guajira.backend.Controller;

import com.ecotur.guajira.backend.Entities.Dto.PostDTO;
import com.ecotur.guajira.backend.Entities.Post;
import com.ecotur.guajira.backend.Entities.PostType;
import com.ecotur.guajira.backend.Services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // GET /api/posts - Obtener todos los posts
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        try {
            List<Post> posts = postService.getAllPosts();
            List<PostDTO> postDTOs = posts.stream()
                    .map(PostDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/posts/paginated - Obtener posts con paginación
    @GetMapping("/paginated")
    public ResponseEntity<Page<PostDTO>> getPostsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recent") String sortBy) {
        try {
            Page<Post> posts = postService.getPostsPaginated(page, size, sortBy);
            Page<PostDTO> postDTOs = posts.map(PostDTO::new);

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/posts/{id} - Obtener post por ID
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable String id) {
        try {
            Optional<Post> post = postService.getPostById(id);

            if (post.isPresent()) {
                return ResponseEntity.ok(new PostDTO(post.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST /api/posts - Crear nuevo post
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody Post post) {
        try {
            Post createdPost = postService.createPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(new PostDTO(createdPost));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // PUT /api/posts/{id} - Actualizar post
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable String id, @Valid @RequestBody Post postDetails) {
        try {
            Post updatedPost = postService.updatePost(id, postDetails);
            return ResponseEntity.ok(new PostDTO(updatedPost));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /api/posts/{id} - Eliminar post (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /api/posts/{id}/permanent - Eliminar post permanentemente
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<Void> deletePostPermanently(@PathVariable String id) {
        try {
            postService.deletePostPermanently(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST /api/posts/{id}/like - Toggle like en post
    @PostMapping("/{id}/like")
    public ResponseEntity<PostDTO> toggleLike(@PathVariable String id) {
        try {
            Post post = postService.toggleLike(id);
            return ResponseEntity.ok(new PostDTO(post));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/posts/search - Buscar posts
    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam String query) {
        try {
            List<Post> posts = postService.searchPosts(query);
            List<PostDTO> postDTOs = posts.stream()
                    .map(PostDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/posts/type/{type} - Filtrar por tipo
    @GetMapping("/type/{type}")
    public ResponseEntity<List<PostDTO>> getPostsByType(@PathVariable PostType type) {
        try {
            List<Post> posts = postService.getPostsByType(type);
            List<PostDTO> postDTOs = posts.stream()
                    .map(PostDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/posts/author/{authorName} - Posts por autor
    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<PostDTO>> getPostsByAuthor(@PathVariable String authorName) {
        try {
            List<Post> posts = postService.getPostsByAuthor(authorName);
            List<PostDTO> postDTOs = posts.stream()
                    .map(PostDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/posts/stats/{authorName} - Estadísticas de posts
    @GetMapping("/stats/{authorName}")
    public ResponseEntity<PostService.PostStats> getPostStats(@PathVariable String authorName) {
        try {
            PostService.PostStats stats = postService.getPostStats(authorName);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
