package com.backend.pocketTrack.exceptions;

public class ErrorGenericoException extends RuntimeException {
    public <T> ErrorGenericoException(String error, Throwable cause)
    {
        super(error, cause);
    }
}
