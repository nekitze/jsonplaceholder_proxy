package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(Long id);

    Post addNewPost(Post post);

    Post updatePost(Post post);

    String deletePost(Long id);
}
