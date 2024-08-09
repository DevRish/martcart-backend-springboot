package com.devrish.martcart.exception.category;

public class CategoryNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Category not found";
    }
}
