package com.devrish.martcart.exception.auth;

public class InvalidCredentialsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid Credentials!";
    }
}
