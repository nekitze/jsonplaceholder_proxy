package edu.nikitazubov.jsonplaceholderproxy.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users", schema = "jsonplaceholder_proxy")
public class ProxyUser {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;
}
