package com.nabil.managers.config;

import java.io.IOException;

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
        
        if(request.getMethod().equalsIgnoreCase("POST") && request.getRequestURI().endsWith("/login") ){
            
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
                
                try {
                    filterChain.doFilter(wrappedRequest, response); 
                } finally {
                    String username =  request.getParameter("username");
                    String locationHeader = response.getHeader("Location");
                    String loginStatus = (response.getStatus() == 302 && locationHeader != null && locationHeader.endsWith("/home")) ? "SUCCESS" : "FAILED";
                    LoginLogEvent logData = new LoginLogEvent(request, response,loginStatus);
                    if (loginStatus.equals("SUCCESS")) {
                        logData.setMessage("User " + username +" successfully established session and granted access.");
                        logger.info(logData.toJsonString()); 
                    } else{
                        logger.warn(logData.toJsonString()); 
                    }
            }
        }

        filterChain.doFilter(request, response);
    }
}