package edu.nikitazubov.jsonplaceholderproxy.configuration;

import edu.nikitazubov.jsonplaceholderproxy.audit.RequestAuditFilter;
import edu.nikitazubov.jsonplaceholderproxy.service.ProxyUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html,",
            "/api/proxy/add_user/"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestAuditFilter requestAuditFilter) throws Exception {
        http
                .addFilterBefore(requestAuditFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/posts/**").hasRole("POSTS_EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/api/posts/**").hasRole("POSTS_EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").hasRole("POSTS_EDITOR")

                        .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("USERS_EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("USERS_EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("USERS_EDITOR")

                        .requestMatchers(HttpMethod.POST, "/api/albums/**").hasRole("ALBUMS_EDITOR")
                        .requestMatchers(HttpMethod.PUT, "/api/albums/**").hasRole("ALBUMS_EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/albums/**").hasRole("ALBUMS_EDITOR")

                        .requestMatchers(HttpMethod.GET, "/api/posts/**").hasRole("POSTS_VIEWER")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("USERS_VIEWER")
                        .requestMatchers(HttpMethod.GET, "/api/albums/**").hasRole("ALBUMS_VIEWER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
                ROLE_ADMIN > ROLE_POSTS
                ROLE_ADMIN > ROLE_USERS
                ROLE_ADMIN > ROLE_ALBUMS
                ROLE_POSTS > ROLE_POSTS_EDITOR > ROLE_POSTS_VIEWER
                ROLE_USERS > ROLE_USERS_EDITOR > ROLE_USERS_VIEWER
                ROLE_ALBUMS > ROLE_ALBUMS_EDITOR > ROLE_ALBUMS_VIEWER"""
        );
        return hierarchy;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new ProxyUserService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
