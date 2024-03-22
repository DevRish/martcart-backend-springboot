package com.devrish.martcart.controller;

import com.devrish.martcart.dto.responses.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping("/")
    public GenericResponse getCategories() {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @GetMapping("/{id}")
    public GenericResponse getCategoryById(@PathVariable String id) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
