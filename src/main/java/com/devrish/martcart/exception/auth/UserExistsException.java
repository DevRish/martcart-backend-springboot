package com.devrish.martcart.exception.auth;

public class UserExistsException extends Exception {

    @Override
    public String getMessage() {
        return "User already exists!";
    }
}
