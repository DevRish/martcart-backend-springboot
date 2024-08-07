package com.devrish.martcart.service;

import com.devrish.martcart.dto.requests.auth.LoginBody;
import com.devrish.martcart.dto.responses.AuthResponse;
import com.devrish.martcart.exception.auth.InvalidCredentialsException;
import com.devrish.martcart.exception.auth.UserNotFoundException;
import com.devrish.martcart.model.Session;
import com.devrish.martcart.model.User;
import com.devrish.martcart.repository.SessionRepository;
import com.devrish.martcart.repository.UserRepository;
import com.devrish.martcart.util.JWT.JWTHeader;
import com.devrish.martcart.util.JWT.JWTPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    public AuthResponse login(LoginBody body) throws Exception {
        User existingUser = userRepository.findByUsername(body.getUsername()).orElse(null);
        if (existingUser == null) throw new UserNotFoundException();

        // check if password matches
        if (!existingUser.getPassword().equals(body.getPassword())) throw new InvalidCredentialsException();

        // passwords matching
        Session newSession = sessionRepository.save(Session.builder().userId(existingUser).build());

        // JWT : header, payload, signature
        JWTHeader header = JWTHeader.builder().alg("HS512").typ("JWT").build();
        String encodedHeader = encodeBase64Url(header);

        JWTPayload payload = JWTPayload.builder().id(newSession.get_id().toString()).build();
        String encodedPayload = encodeBase64Url(payload);

        Mac hashFunc = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hashFunc.init(secretKey);

        String data = encodedHeader + "." + encodedPayload;
        String signature = encodeBase64Url(hashFunc.doFinal(data.getBytes(StandardCharsets.UTF_8)));

        // log.warn("Encoded header: {}", encodedHeader);
        // log.warn("Encoded payload: {}", encodedPayload);
        // log.warn("Encoded signature: {}", signature);

        String jwtToken = encodedHeader + "." + encodedPayload + "." + signature;

        return new AuthResponse(
                true,
                "Logged in successfully!",
                jwtToken,
                new AuthResponse.AuthUser(
                        existingUser.getFirstname(),
                        existingUser.getLastname(),
                        existingUser.getCart()
                )
        );
    }

    private String encodeBase64Url(Object obj) throws Exception {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
                objectMapper.writeValueAsString(obj).getBytes(StandardCharsets.UTF_8)
        );
    }

    private String encodeBase64Url(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
