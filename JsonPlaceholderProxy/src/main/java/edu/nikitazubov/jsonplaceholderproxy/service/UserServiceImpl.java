package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.model.User;
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
public class UserServiceImpl implements UserService {

    private static final String TARGET_URL = "https://jsonplaceholder.typicode.com/users/";
    private final RestTemplate restTemplate;

    public UserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable("users")
    @Override
    public List<User> getAllUsers() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                TARGET_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @Cacheable(value = "users", key = "#id")
    @Override
    public User getUserById(Long id) {
        try {
            return restTemplate.getForObject(TARGET_URL + id, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    @CachePut(value = "users", key = "#result.id")
    @Override
    public User addNewUser(User user) {
        return restTemplate.postForObject(TARGET_URL, user, User.class);
    }

    @CachePut(value = "users", key = "#user.id")
    @Override
    public User updateUser(User user) {
        restTemplate.put(TARGET_URL + user.getId(), user);
        return user;
    }

    @CacheEvict(value = "users", key = "#id")
    @Override
    public String deleteUser(Long id) {
        restTemplate.delete(TARGET_URL + id);
        return "User deleted.";
    }
}