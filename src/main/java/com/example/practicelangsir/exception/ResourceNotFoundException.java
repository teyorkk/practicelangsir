package com.example.practicelangsir.exception;

public class ResourceNotFoundException extends OrderException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " not found with id: " + id);
    }
}
