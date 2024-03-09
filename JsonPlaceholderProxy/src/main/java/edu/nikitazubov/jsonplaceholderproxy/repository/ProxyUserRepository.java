package edu.nikitazubov.jsonplaceholderproxy.repository;

import edu.nikitazubov.jsonplaceholderproxy.entity.ProxyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProxyUserRepository extends JpaRepository<ProxyUser, Long> {
}
