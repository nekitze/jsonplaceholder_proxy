package edu.nikitazubov.jsonplaceholderproxy.audit;

import edu.nikitazubov.jsonplaceholderproxy.entity.AuditLog;
import edu.nikitazubov.jsonplaceholderproxy.repository.AuditLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestAuditFilter extends OncePerRequestFilter {
    private final AuditLogRepository auditLogRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            AuditLog auditLog = new AuditLog();
            auditLog.setDateTime(LocalDateTime.now());
            auditLog.setMethod(request.getMethod());
            auditLog.setUrl(request.getRequestURI());

            filterChain.doFilter(request, response);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                auditLog.setUserName(authentication.getName());
            }

            int status = response.getStatus();
            auditLog.setHasAccess(status != HttpServletResponse.SC_UNAUTHORIZED && status != HttpServletResponse.SC_FORBIDDEN);

            auditLogRepository.save(auditLog);
            log.info(auditLog.toString());
        } catch (Exception e) {
            log.error("Error processing request", e);
        }
    }
}
