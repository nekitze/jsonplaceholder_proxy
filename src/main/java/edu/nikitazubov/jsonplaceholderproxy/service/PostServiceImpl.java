package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.model.Post;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private static final String TARGET_URL = "https://jsonplaceholder.typicode.com/posts/";
    private final RestTemplate restTemplate;

    public PostServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable("posts")
    @Override
    public List<Post> getAllPosts() {
        ResponseEntity<List<Post>> response = restTemplate.exchange(
                TARGET_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @Cacheable(value = "posts", key = "#id")
    @Override
    public Post getPostById(Long id) {
        try {
            return restTemplate.getForObject(TARGET_URL + id, Post.class);
        } catch (Exception e) {
            return null;
        }
    }

    @CachePut(value = "posts", key = "#result.id")
    @Override
    public Post addNewPost(Post post) {
        return restTemplate.postForObject(TARGET_URL, post, Post.class);
    }

    @CachePut(value = "posts", key = "#post.id")
    @Override
    public Post updatePost(Post post) {
        restTemplate.put(TARGET_URL + post.getId(), post);
        return post;
    }

    @CacheEvict(value = "posts", key = "#id")
    @Override
    public String deletePost(Long id) {
        restTemplate.delete(TARGET_URL + id);
        return "Post deleted.";
    }
}
