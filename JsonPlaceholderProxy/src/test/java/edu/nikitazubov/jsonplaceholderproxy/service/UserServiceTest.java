package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.model.User;
import edu.nikitazubov.jsonplaceholderproxy.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Objects;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    CacheManager cacheManager;

    private final static User TEST_USER = new User(
            "Ivan"
            , "Testov"
            , "abc@email.com"
            , null, null, null, null);

    @BeforeEach
    @CacheEvict(value = "users", allEntries = true)
    public void clearCache() {
    }

    @Test
    public void getAllUsersCaching() {
        String users = userService.getAllUsers().toString();
        String cached = Objects.requireNonNull(cacheManager.getCache("users")).getNativeCache().toString();
        Assertions.assertTrue(cached.contains(users));
    }

    @Test
    public void addNewUserCaching() {
        User user = userService.addNewUser(TEST_USER);
        User cachedUser = userService.getUserById(user.getId());
        Assertions.assertEquals(user, cachedUser);
    }

    @Test
    public void updateUserCaching() {
        User user = userService.getUserById(1L);
        user.setName("UPDATED");
        user.setUsername("UPDATED");
        userService.updateUser(user);
        User cachedUpdatedUser = userService.getUserById(user.getId());
        Assertions.assertEquals(user, cachedUpdatedUser);
    }

    @Test
    public void deleteUserCaching() {
        User user = userService.addNewUser(TEST_USER);
        Assertions.assertNotNull(userService.getUserById(user.getId()));
        userService.deleteUser(user.getId());
        Assertions.assertNull(userService.getUserById(user.getId()));
    }
}
