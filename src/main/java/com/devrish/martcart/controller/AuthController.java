package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.auth.LoginBody;
import com.devrish.martcart.dto.requests.auth.SignupBody;
import com.devrish.martcart.dto.responses.AuthResponse;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.exception.auth.InvalidCredentialsException;
import com.devrish.martcart.exception.auth.UserExistsException;
import com.devrish.martcart.exception.auth.UserNotFoundException;
import com.devrish.martcart.model.User;
import com.devrish.martcart.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.devrish.martcart.util.validation.ValidationUtils.generateValidationResult;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@Valid @RequestBody LoginBody body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return generateValidationResult(bindingResult);
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
    public ResponseEntity<GenericResponse> signup(@Valid @RequestBody SignupBody body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return generateValidationResult(bindingResult);
        try {
            AuthResponse res = authService.signup(
                    User.builder()
                            .firstname(body.getFirstname())
                            .lastname(body.getLastname())
                            .username(body.getUsername())
                            .password(body.getPassword())
                            .email(body.getEmail())
                            .phone(body.getPhone())
                            .userType(body.getUserType())
                            .build()
            );
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (UserExistsException e) {
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

    @PostMapping("/logout")
    public ResponseEntity<GenericResponse> logout(@RequestAttribute(name = "currentUser") User currentUser) {
        try {
            AuthResponse res = authService.logout(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );

        }
    }

}
