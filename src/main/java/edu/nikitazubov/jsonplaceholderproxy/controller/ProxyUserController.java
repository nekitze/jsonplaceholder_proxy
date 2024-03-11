package edu.nikitazubov.jsonplaceholderproxy.controller;

import edu.nikitazubov.jsonplaceholderproxy.entity.ProxyUser;
import edu.nikitazubov.jsonplaceholderproxy.exception.IncorrectRequestException;
import edu.nikitazubov.jsonplaceholderproxy.service.ProxyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/proxy/")
public class ProxyUserController {

    private final ProxyUserService userDetailsService;

    @PostMapping("add_user/")
    public String addUser(@RequestBody ProxyUser user) {
        try {
            userDetailsService.addUser(user);
        } catch (Exception e) {
            throw new IncorrectRequestException(e.getMessage());
        }
        return "User created";
    }
}
