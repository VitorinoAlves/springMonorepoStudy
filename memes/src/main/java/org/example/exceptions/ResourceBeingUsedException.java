package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceBeingUsedException extends RuntimeException{
    public ResourceBeingUsedException(String message) {
        super(message);
    }
}
