package edu.nikitazubov.jsonplaceholderproxy.repository;

import edu.nikitazubov.jsonplaceholderproxy.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
}
