package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;

import java.util.*;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    CacheManager cacheManager;

    private final static Post TEST_POST = new Post(1L, "Post", "Post body");

    @BeforeEach
    @CacheEvict(value = "posts", allEntries = true)
    public void clearCache() {
    }

    @Test
    public void getAllPostsCaching() {
        String posts = postService.getAllPosts().toString();
        String cached = Objects.requireNonNull(cacheManager.getCache("posts")).getNativeCache().toString();
        Assertions.assertTrue(cached.contains(posts));
    }

    @Test
    public void addNewPostCaching() {
        Post post = postService.addNewPost(TEST_POST);
        Post cachedPost = postService.getPostById(post.getId());
        Assertions.assertEquals(post, cachedPost);
    }

    @Test
    public void updatePostCaching() {
        Post post = postService.getPostById(1L);
        post.setBody("UPDATED");
        post.setTitle("UPDATED");
        postService.updatePost(post);
        Post cachedUpdatedPost = postService.getPostById(post.getId());
        Assertions.assertEquals(post, cachedUpdatedPost);
    }

    @Test
    public void deletePostCaching() {
        Post post = postService.addNewPost(TEST_POST);
        Assertions.assertNotNull(postService.getPostById(post.getId()));
        postService.deletePost(post.getId());
        Assertions.assertNull(postService.getPostById(post.getId()));
    }
}
