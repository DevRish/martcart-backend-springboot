package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.auth.LoginBody;
import com.devrish.martcart.dto.requests.auth.SignupBody;
import com.devrish.martcart.dto.responses.AuthResponse;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.exception.auth.InvalidCredentialsException;
import com.devrish.martcart.exception.auth.UserNotFoundException;
import com.devrish.martcart.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody LoginBody body) {
        try {
            AuthResponse res = authService.login(body);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    GenericResponse.builder().status(false).message(e.getMessage()).build()
            );
        } catch(InvalidCredentialsException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    GenericResponse.builder().status(false).message(e.getMessage()).build()
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

}
