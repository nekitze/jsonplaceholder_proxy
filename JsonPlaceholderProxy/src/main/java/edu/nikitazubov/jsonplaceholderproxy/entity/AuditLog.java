package edu.nikitazubov.jsonplaceholderproxy.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.processing.SQL;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@ToString
@Table(name = "audit", schema = "jsonplaceholder_proxy")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "has_access")
    private boolean hasAccess;

    @Column(name = "method")
    private String method;

    @Column(name = "url")
    private String url;
}
