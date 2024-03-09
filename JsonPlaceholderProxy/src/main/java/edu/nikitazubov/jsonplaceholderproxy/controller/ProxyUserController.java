package edu.nikitazubov.jsonplaceholderproxy.controller;

import edu.nikitazubov.jsonplaceholderproxy.entity.ProxyUser;
import edu.nikitazubov.jsonplaceholderproxy.service.ProxyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/proxy/users")
public class ProxyUserController {

    private final ProxyUserDetailsService userDetailsService;

    @PostMapping("/")
    public String addUser(@RequestBody ProxyUser user) {
        userDetailsService.addUser(user);
        return "User created";
    }
}
