package com.devrish.martcart.filter;

import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.model.Session;
import com.devrish.martcart.model.User;
import com.devrish.martcart.repository.SessionRepository;
import com.devrish.martcart.repository.UserRepository;
import com.devrish.martcart.util.jwt.JWTPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            // log.info("AUTH FILTER RUNNING");
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null) {
                sendUnauthorizedAccessResponse(response);
                log.error("No Authorization header in request");
                return;
            }

            if (authHeader.split("\\s+").length <= 1) {
                sendUnauthorizedAccessResponse(response);
                log.error("Invalid auth header");
                return;
            }

            String jwtToken = authHeader.split("\\s+")[1];

            String[] jwtTokenParts = jwtToken.split("\\.");
            if (jwtTokenParts.length != 3) {
                sendUnauthorizedAccessResponse(response);
                log.error("Invalid jwt token");
                return;
            }

            String encodedHeader = jwtTokenParts[0];
            String encodedPayload = jwtTokenParts[1];
            String signature = jwtTokenParts[2];

            // log.warn("Received header: {}", encodedHeader);
            // log.warn("Received payload: {}", encodedPayload);
            // log.warn("Received signature: {}", signature);

            Mac hashFunc = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hashFunc.init(secretKey);

            String data = encodedHeader + "." + encodedPayload;
            byte[] expectedSignatureBytes = hashFunc.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String expectedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(expectedSignatureBytes);

            if (!signature.equals(expectedSignature)) {
                sendUnauthorizedAccessResponse(response);
                log.error("Invalid jwt token");
                // log.warn("Expected Signature: {}", expectedSignature);
                return;
            }

            String decodedPayload = new String(Base64.getUrlDecoder().decode(encodedPayload), StandardCharsets.UTF_8);
            JWTPayload payload = objectMapper.readValue(decodedPayload, JWTPayload.class);

            Session session = sessionRepository.findById(payload.getId()).orElse(null);

            if (session == null) {
                sendUnauthorizedAccessResponse(response);
                log.error("Session invalid or expired");
                return;
            }

            User user = session.getUserId();

            request.setAttribute("currentUser", user); // this can be later accessed from the controller
            filterChain.doFilter(request, response); // continue to controller / next filter

        } catch (Exception e) {
            log.error(e.getMessage());
            sendUnauthorizedAccessResponse(response);
        }

    }

    private void sendUnauthorizedAccessResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(
                GenericResponse.builder().status(false).message("Unauthorized Access").build()
        ));
    }
}
