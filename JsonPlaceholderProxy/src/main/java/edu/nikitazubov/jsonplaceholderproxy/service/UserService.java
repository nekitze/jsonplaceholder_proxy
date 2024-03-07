package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User addNewUser(User user);

    User updateUser(User user);

    String deleteUser(Long id);
}
