package com.devrish.martcart.util.validation;

import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.dto.responses.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class ValidationUtils {

    public static ResponseEntity<GenericResponse> generateValidationResult(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ValidationResponse(
                        false,
                        "Validation Error!",
                        bindingResult.getAllErrors().stream()
                                .map(ObjectError::getDefaultMessage)
                                .collect(Collectors.toList())
                )
        );
    }

}
