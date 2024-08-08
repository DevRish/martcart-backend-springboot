package com.devrish.martcart.exception.cart;

public class ProductNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Product does not exist!";
    }
}
