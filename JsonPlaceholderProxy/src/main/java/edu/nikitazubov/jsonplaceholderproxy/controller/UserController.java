package edu.nikitazubov.jsonplaceholderproxy.controller;

import edu.nikitazubov.jsonplaceholderproxy.entity.User;
import edu.nikitazubov.jsonplaceholderproxy.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/")
    public User addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
