package edu.nikitazubov.jsonplaceholderproxy.audit;

import edu.nikitazubov.jsonplaceholderproxy.entity.AuditLog;
import edu.nikitazubov.jsonplaceholderproxy.repository.AuditLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RequestAuditFilter extends OncePerRequestFilter {

    private final AuditLogRepository auditLogRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain)
            throws ServletException, IOException {
        AuditLog auditLog = new AuditLog();
        auditLog.setDateTime(LocalDateTime.now());
        filterChain.doFilter(request, response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int status = response.getStatus();
        boolean hasAccess = status != HttpServletResponse.SC_UNAUTHORIZED
                && status != HttpServletResponse.SC_FORBIDDEN;
        if (authentication != null) {
            auditLog.setUserName(authentication.getName());
        }
        auditLog.setMethod(request.getMethod());
        auditLog.setUrl(request.getRequestURI());
        auditLog.setHasAccess(hasAccess);

        auditLogRepository.save(auditLog);
        System.out.println(auditLog);
    }
}
