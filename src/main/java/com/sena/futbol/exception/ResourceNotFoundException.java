package com.sena.futbol.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String recurso, Integer id) {
        super(recurso + " con id " + id + " no existe");
    }
}
