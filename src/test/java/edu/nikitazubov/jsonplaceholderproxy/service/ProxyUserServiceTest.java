package edu.nikitazubov.jsonplaceholderproxy.service;

import edu.nikitazubov.jsonplaceholderproxy.configuration.TestConfig;
import edu.nikitazubov.jsonplaceholderproxy.entity.ProxyUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class ProxyUserServiceTest {

    @Autowired
    ProxyUserService proxyUserService;

    @Test
    public void addNewUserPositive() {
        ProxyUser user = new ProxyUser();
        user.setName("Username");
        user.setPassword("password");
        user.setRoles("ROLE_POSTS");

        Assertions.assertDoesNotThrow(() -> proxyUserService.addUser(user));
    }

    @Test
    public void addNewUserNegative() {
        ProxyUser user = new ProxyUser();
        user.setName("Username");
        user.setPassword("password");
        user.setRoles(null);

        Assertions.assertThrows(Exception.class, () -> proxyUserService.addUser(user));
    }

    @Test
    public void getUserDetails() {
        UserDetails userDetails;

        ProxyUser user = new ProxyUser();
        user.setName("user_with_roles");
        user.setPassword("password");
        user.setRoles("ROLE_POSTS, ROLE_ALBUMS");

        Assertions.assertDoesNotThrow(() -> proxyUserService.addUser(user));
        userDetails = proxyUserService.loadUserByUsername("user_with_roles");

        String nameDetail = userDetails.getUsername();
        List<String> rolesDetail = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).
                toList();

        Assertions.assertEquals(user.getName(), nameDetail);
        Assertions.assertTrue(rolesDetail.contains("ROLE_POSTS"));
        Assertions.assertTrue(rolesDetail.contains("ROLE_ALBUMS"));
    }
}
