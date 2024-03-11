package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.configuration.TestConfig;
import edu.nikitazubov.jsonplaceholderproxy.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
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
    public void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("users")).clear();
    }

    @Test
    public void getUserByID() {
        Long expectedId = 1L;
        String expectedName = "Leanne Graham";
        String expectedUsername = "Bret";
        String expectedPhone = "1-770-736-8031 x56442";

        User actual = userService.getUserById(1L);

        Assertions.assertEquals(expectedId, actual.getId());
        Assertions.assertEquals(expectedName, actual.getName());
        Assertions.assertEquals(expectedUsername, actual.getUsername());
        Assertions.assertEquals(expectedPhone, actual.getPhone());
    }

    @Test
    public void getUserByIDNotExisting() {
        User actual = userService.getUserById(666L);
        Assertions.assertNull(actual);
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
