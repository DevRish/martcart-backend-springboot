package com.devrish.martcart.dto.responses;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationResponse extends GenericResponse {
    private List<String> errors;

    public ValidationResponse(Boolean status, String message, List<String> errors) {
        super(status, message);
        this.errors = errors;
    }
}
