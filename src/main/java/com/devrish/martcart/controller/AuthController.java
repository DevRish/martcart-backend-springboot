package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.auth.LoginBody;
import com.devrish.martcart.dto.requests.auth.SignupBody;
import com.devrish.martcart.dto.responses.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @PostMapping("/login")
    public GenericResponse login(@RequestBody LoginBody body) {
        log.info("Received Username: " + body.getUsername());
        log.info("Received Password: " + body.getPassword());
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping("/signup")
    public GenericResponse signup(@RequestBody SignupBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping("/logout")
    public GenericResponse logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
