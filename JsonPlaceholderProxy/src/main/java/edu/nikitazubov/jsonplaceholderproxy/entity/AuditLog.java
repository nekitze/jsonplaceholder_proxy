package edu.nikitazubov.jsonplaceholderproxy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "audit.logs")
@NoArgsConstructor
@ToString
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column
    private LocalDateTime dateTime;

    @Column
    private String userName;

    @Column
    private boolean hasAccess;

    @Column
    private String method;

    @Column
    private String url;
}
