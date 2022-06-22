package com.ochokor.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeUserNotFound extends RuntimeException{

    public RecipeUserNotFound(String message) {
        super(message);
    }

    public RecipeUserNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
