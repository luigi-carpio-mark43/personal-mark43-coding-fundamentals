package com.mark43.practice;

/**
 * Pre-built for ch04. Do not edit.
 *
 * Thrown when a user attempts an operation they don't have the ability for.
 * Unchecked (extends RuntimeException) so it doesn't need to be declared in throws clauses.
 */
public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(String message) {
        super(message);
    }
}
