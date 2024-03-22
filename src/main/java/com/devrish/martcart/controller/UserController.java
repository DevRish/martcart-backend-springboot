package com.devrish.martcart.controller;

import com.devrish.martcart.dto.responses.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/getUser")
    public GenericResponse getUserData() {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
