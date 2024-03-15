package org.example.demo2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final List<Post> posts = new ArrayList<>();

    // Insert post
    @PostMapping
    public ResponseEntity<PostResponse<Post>> insertPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse<>("This post was successfully created", post, HttpStatus.CREATED));
    }

    // Read all posts with dynamic pagination
    @GetMapping
    public ResponseEntity<PostResponse<List<Post>>> readAllPosts(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        int totalPosts = posts.size();
        int totalPages = (int) Math.ceil((double) totalPosts / size);

        List<Post> paginatedPosts = posts.stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PostResponse<>("All posts retrieved successfully", paginatedPosts, totalPosts, page, size, totalPages, HttpStatus.OK));
    }

    // Read post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse<Post>> readPostById(@PathVariable int id) {
        Post post = findPostById(id);
        if (post != null) {
            return ResponseEntity.ok(new PostResponse<>("This post has found successfully", post, HttpStatus.OK));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Read post by title
    @GetMapping("/search")
    public ResponseEntity<PostResponse<List<Post>>> readPostByTitle(@RequestParam String title) {
        List<Post> filteredPosts = posts.stream()
                .filter(post -> post.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());

        if (!filteredPosts.isEmpty()) {
            return ResponseEntity.ok(new PostResponse<>("Posts with title found successfully", filteredPosts, HttpStatus.OK));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Read post by author
    @GetMapping("/searchByAuthor")
    public ResponseEntity<PostResponse<List<Post>>> readPostByAuthor(@RequestParam String author) {
        List<Post> filteredPosts = posts.stream()
                .filter(post -> post.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());

        if (!filteredPosts.isEmpty()) {
            return ResponseEntity.ok(new PostResponse<>("Posts by author found successfully", filteredPosts, HttpStatus.OK));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Read post by multiple tags
    @GetMapping("/searchByTags")
    public ResponseEntity<PostResponse<List<Post>>> readPostByTags(@RequestParam List<String> tags) {
        List<Post> filteredPosts = posts.stream()
                .filter(post -> post.getTags().containsAll(tags))
                .collect(Collectors.toList());

        if (!filteredPosts.isEmpty()) {
            return ResponseEntity.ok(new PostResponse<>("Posts with tags found successfully", filteredPosts, HttpStatus.OK));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse<Post>> updatePostById(@PathVariable int id, @RequestBody Post updatedPost) {
        Post existingPost = findPostById(id);
        if (existingPost != null) {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());
            existingPost.setAuthor(updatedPost.getAuthor());
            existingPost.setCreationDate(updatedPost.getCreationDate());
            existingPost.setTags(updatedPost.getTags());
            return ResponseEntity.ok(new PostResponse<>("Post updated successfully", existingPost, HttpStatus.OK));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete post by id
    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponse<Post>> deletePostById(@PathVariable int id) {
        boolean removed = posts.removeIf(p -> p.getId() == id);
        if (removed) {
            return ResponseEntity.ok(new PostResponse<>("Post deleted successfully", HttpStatus.OK));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Post findPostById(int id) {
        return posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
