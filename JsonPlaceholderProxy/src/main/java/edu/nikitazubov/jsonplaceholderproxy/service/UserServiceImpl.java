package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.User;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    @Override
    public User getUserById(Long id) {
        return restTemplate.getForObject(TARGET_URL + id, User.class);
    }

    @Override
    public User addNewUser(User user) {
        return restTemplate.postForObject(TARGET_URL, user, User.class);
    }

    @Override
    public User updateUser(User user) {
        restTemplate.put(TARGET_URL, user);
        return user;
    }

    @Override
    public String deleteUser(Long id) {
        restTemplate.delete(TARGET_URL + id);
        return "User with id=" + id + " has been deleted.";
    }
}