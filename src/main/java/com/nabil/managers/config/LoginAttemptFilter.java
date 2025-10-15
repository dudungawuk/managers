package com.nabil.managers.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.nabil.managers.dto.log.LoginLogEvent;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class LoginAttemptFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if(request.getMethod().equalsIgnoreCase("POST")){
            
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
                
                try {
                    
                    filterChain.doFilter(wrappedRequest, response); 
                } finally {
                    byte[] content = wrappedRequest.getContentAsByteArray();
                    String payload = new String(content, StandardCharsets.UTF_8);
                    String locationHeader = response.getHeader("Location");
                    String loginStatus = (response.getStatus() == 302 && locationHeader != null && locationHeader.endsWith("/home")) ? "SUCCESS" : "FAILED";
                    
                    String censoredPayload = payload.replaceAll("password=[^&]*", "password=********");
                    String logData = new LoginLogEvent(request, response,loginStatus, censoredPayload).toJsonString();
                    if (loginStatus.equals("SUCCESS")) {
                        logger.info(logData); // Level INFO untuk keberhasilan
                    } else {
                        logger.warn(logData); // Level WARN untuk kegagalan (deteksi serangan)
                    }
                
            }
        }

        filterChain.doFilter(request, response);
    }
}