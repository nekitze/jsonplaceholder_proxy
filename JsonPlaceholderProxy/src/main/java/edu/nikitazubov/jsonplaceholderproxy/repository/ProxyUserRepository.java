package edu.nikitazubov.jsonplaceholderproxy.repository;

import edu.nikitazubov.jsonplaceholderproxy.entity.ProxyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProxyUserRepository extends JpaRepository<ProxyUser, Long> {
    Optional<ProxyUser> findProxyUserByName(String username);
}
