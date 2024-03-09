package edu.nikitazubov.jsonplaceholderproxy.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "security.users")
public class ProxyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;
}
