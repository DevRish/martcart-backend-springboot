package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.auth.LoginBody;
import com.devrish.martcart.dto.requests.auth.SignupBody;
import com.devrish.martcart.dto.responses.AuthResponse;
import com.devrish.martcart.dto.responses.GenericResponse;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody LoginBody body) {
        try {
            User existingUser = userRepository.findByUsername(body.getUsername()).orElse(null);
            if (existingUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        GenericResponse.builder().status(false).message("User doesn't exist!").build()
                );
            }

            // check if password matches
            if (!existingUser.getPassword().equals(body.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        GenericResponse.builder().status(false).message("Wrong password!").build()
                );
            }

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

            log.warn("Encoded header: {}", encodedHeader);
            log.warn("Encoded payload: {}", encodedPayload);
            log.warn("Encoded signature: {}", signature);

            String jwtToken = encodedHeader + "." + encodedPayload + "." + signature;

            return ResponseEntity.status(HttpStatus.OK).body(
                    new AuthResponse(
                            true,
                            "Logged in successfully!",
                            jwtToken,
                            new AuthResponse.AuthUser(
                                    existingUser.getFirstname(),
                                    existingUser.getLastname(),
                                    existingUser.getCart()
                            )
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @PostMapping("/signup")
    public GenericResponse signup(@RequestBody SignupBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping("/logout")
    public GenericResponse logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    private String encodeBase64Url(Object obj) throws Exception {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(
                objectMapper.writeValueAsString(obj).getBytes(StandardCharsets.UTF_8)
        );
    }

    private String encodeBase64Url(byte[] bytes) throws Exception {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
