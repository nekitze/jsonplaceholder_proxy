package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;

import java.util.*;

@SpringBootTest
@TestConfiguration
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    CacheManager cacheManager;

    private final static Post TEST_POST = new Post(1L, "Post", "Post body");

    @BeforeEach
    public void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("posts")).clear();
    }

    @Test
    public void getPostByID() {
        Post expected = new Post();
        expected.setId(1L);
        expected.setUserId(1L);
        expected.setTitle("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        expected.setBody("""
                quia et suscipit
                suscipit recusandae consequuntur expedita et cum
                reprehenderit molestiae ut ut quas totam
                nostrum rerum est autem sunt rem eveniet architecto""");

        Post actual = postService.getPostById(1L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getPostByIDNotExisting() {
        Post actual = postService.getPostById(666L);
        Assertions.assertNull(actual);
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
