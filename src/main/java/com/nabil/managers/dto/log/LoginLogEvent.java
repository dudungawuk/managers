package com.nabil.managers.dto.log;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginLogEvent {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @JsonProperty("event_id")
    public final String eventId = "LOGIN_ATTEMPT";

    @JsonProperty("timestamp")
    public final String timestamp;

    @JsonProperty("client_ip")
    public final String clientIp;

    @JsonProperty("user_agent")
    public final String userAgent;

    @JsonProperty("target_user")
    public final String targetUsername;

    @JsonProperty("login_status")
    public final String loginStatus;

    @JsonProperty("http_status_code")
    public final int httpStatusCode;

    @JsonProperty("redirect_location")
    public final String redirectLocation;
    
    @JsonProperty("message")
    private String message;

    public LoginLogEvent(HttpServletRequest request, HttpServletResponse response, String loginStatus) {
        this.clientIp = request.getRemoteAddr();
        this.userAgent = request.getHeader("User-Agent");
        this.timestamp = ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toString();
        this.targetUsername = request.getParameter("username"); // Aman, sudah ada di wrapped request cache
        this.loginStatus = loginStatus;
        this.httpStatusCode = response.getStatus();
        this.redirectLocation = response.getHeader("Location");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toJsonString() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{ \"error\": \"Gagal serialisasi log: " + e.getMessage() + "\" }";
        }
    }
}