package com.devrish.martcart.controller;

import com.devrish.martcart.dto.responses.CategoryResponse;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.exception.category.CategoryNotFoundException;
import com.devrish.martcart.model.Category;
import com.devrish.martcart.service.CategoryService;
import com.devrish.martcart.service.ValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ValidationService validationService;

    @PostMapping
    public ResponseEntity<GenericResponse> createCategory(
            @Valid @RequestBody Category category,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return validationService.generateValidationResult(bindingResult);
        try {
            CategoryResponse res = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getCategories() {
        try {
            CategoryResponse res = categoryService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getCategoryById(
            @Valid @PathVariable @NotNull(message = "id is required") String id
    ) {
        try {
            CategoryResponse res = categoryService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch(CategoryNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    GenericResponse.builder().status(false).message(e.getMessage()).build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

}
