package com.devrish.martcart.exception.event;

public class EventNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Event not found";
    }
}
