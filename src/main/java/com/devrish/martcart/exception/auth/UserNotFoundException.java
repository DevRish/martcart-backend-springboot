package com.devrish.martcart.exception.auth;

public class UserNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "User doesn't exist!";
    }

}
