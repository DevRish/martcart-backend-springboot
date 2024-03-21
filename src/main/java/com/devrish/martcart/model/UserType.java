package com.devrish.martcart.model;

public enum UserType {
    BUYER("BUYER"),
    SELLER("SELLER"),
    ADMIN("ADMIN"),
    ;

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
