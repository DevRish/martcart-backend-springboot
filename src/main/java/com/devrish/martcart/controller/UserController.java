package com.devrish.martcart.controller;

import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/getUser")
    public GenericResponse getUserData(@RequestAttribute(name = "currentUser") User currentUser) {
        // have access to currently logged in user using currentUser
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
