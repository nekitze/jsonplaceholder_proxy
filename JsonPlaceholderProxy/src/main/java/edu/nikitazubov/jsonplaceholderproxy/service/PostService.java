package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.Post;

import java.util.List;

public interface PostService {
    public List<Post> getAllPosts();

    public Post getPostById(Long id);

    public Post addNewPost(Post post);

    public Post updatePost(Post post);

    public String deletePost(Long id);
}
