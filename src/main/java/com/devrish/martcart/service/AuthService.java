package com.devrish.martcart.service;

import com.devrish.martcart.dto.requests.auth.LoginBody;
import com.devrish.martcart.dto.responses.AuthResponse;
import com.devrish.martcart.exception.auth.InvalidCredentialsException;
import com.devrish.martcart.exception.auth.UserExistsException;
import com.devrish.martcart.exception.auth.UserNotFoundException;
import com.devrish.martcart.model.Session;
import com.devrish.martcart.model.User;
import com.devrish.martcart.repository.SessionRepository;
import com.devrish.martcart.repository.UserRepository;
import com.devrish.martcart.dto.jwt.JWTHeader;
import com.devrish.martcart.dto.jwt.JWTPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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

    public AuthResponse signup(User user) throws Exception {
        User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUser != null) throw new UserExistsException();

        Date now = new Date();
        String salt = now.toString();
        user.setJoinDate(now);

        Mac hashFunc = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hashFunc.init(secretKey);

        String encodedPassword = encodeHex(hashFunc.doFinal(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        String originalPassword = user.getPassword();
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return login(new LoginBody(user.getUsername(), originalPassword));
    }

    public AuthResponse login(LoginBody body) throws Exception {
        User existingUser = userRepository.findByUsername(body.getUsername()).orElse(null);
        if (existingUser == null) throw new UserNotFoundException();

        // check if password matches
        String saltStr = existingUser.getJoinDate().toString();
        Mac hashFn = Mac.getInstance("HmacSHA512");
        SecretKeySpec salt = new SecretKeySpec(saltStr.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hashFn.init(salt);
        String encodedPassword = encodeHex(hashFn.doFinal(body.getPassword().getBytes(StandardCharsets.UTF_8)));
        if (!existingUser.getPassword().equals(encodedPassword)) throw new InvalidCredentialsException();

        // passwords matching
        Session newSession = sessionRepository.save(Session.builder().userId(existingUser).build());

        // jwt : header, payload, signature
        JWTHeader header = JWTHeader.builder().alg("HS512").typ("jwt").build();
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

    public AuthResponse logout(User currentUser) {
        List<Session> sessions = sessionRepository.findByUserId(currentUser);
        if (sessions.size() > 0) {
            sessionRepository.deleteAll(sessions);
        }
        return new AuthResponse(
                true,
                "Logged out successfully!",
                null,
                null
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

    private static String encodeHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b); // Convert byte to hex string
            if (hex.length() == 1) {
                hexString.append('0'); // Prepend '0' if single digit
            }
            hexString.append(hex); // Append the hex string
        }
        return hexString.toString();
    }

}
