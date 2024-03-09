package edu.nikitazubov.jsonplaceholderproxy.controller;

import edu.nikitazubov.jsonplaceholderproxy.model.Post;
import edu.nikitazubov.jsonplaceholderproxy.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping("/")
    public Post addNewPost(@RequestBody Post post) {
        return postService.addNewPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        return postService.deletePost(id);
    }
}
