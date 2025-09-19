package com.raja.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raja.exception.ApiErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${security.api-key}")
    private String staticKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);
        if (staticKey.equals(apiKey)) {
            filterChain.doFilter(request, response);
        } else {
            writeErrorResponse(response, request.getRequestURI());
        }
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    private void writeErrorResponse(HttpServletResponse response, String path) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ApiErrorResponse error = new ApiErrorResponse();
        error.setMessage("Invalid API Key");
        error.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
        error.setTimestamp(System.currentTimeMillis());
        error.setPath(path);
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(error));
    }
}
