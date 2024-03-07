package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.Post;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PostServicImpl implements PostService {

    private static final String TARGET_URL = "https://jsonplaceholder.typicode.com/albums/";
    private final RestTemplate restTemplate;

    public PostServicImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

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

    @Override
    public Post getPostById(Long id) {
        return restTemplate.getForObject(TARGET_URL + id, Post.class);
    }

    @Override
    public Post addNewPost(Post post) {
        return restTemplate.postForObject(TARGET_URL, post, Post.class);
    }

    @Override
    public Post updatePost(Post post) {
        restTemplate.put(TARGET_URL, post);
        return post;
    }

    @Override
    public String deletePost(Long id) {
        restTemplate.delete(TARGET_URL + id);
        return "Post with id=" + id + " has been deleted.";
    }
}
