package com.backend.pocketTrack.exceptions;

public class CreateEntityException extends RuntimeException {
    public <T> CreateEntityException(String nameClass, T entityClass, Throwable cause)
    {
        super("Error al crear la entidad " + nameClass + " : " + entityClass, cause);
    }
}
