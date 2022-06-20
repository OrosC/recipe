package com.ochokor.recipe.exception;

public class RecipeNotFound extends RuntimeException {

    public RecipeNotFound(String message) {
        super(message);
    }

    public RecipeNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
