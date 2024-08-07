package com.devrish.martcart.controller;

import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.dto.responses.UserResponse;
import com.devrish.martcart.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/getUser")
    public ResponseEntity<GenericResponse> getUserData(@RequestAttribute(name = "currentUser") User currentUser) {
        // hide password and confidential stuff
        User userToSend = User.builder()
                ._id(null)
                .firstname(currentUser.getFirstname())
                .lastname(currentUser.getLastname())
                .phone(currentUser.getPhone())
                .email(currentUser.getEmail())
                .cart(currentUser.getCart())
                .username(currentUser.getUsername())
                .password(null)
                .joinDate(currentUser.getJoinDate())
                .userType(currentUser.getUserType())
                .build();
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new UserResponse(
                        true,
                        "Fetched user details successfully",
                        userToSend
                )
        );
    }

}
