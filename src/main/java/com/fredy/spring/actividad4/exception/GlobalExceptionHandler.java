package com.fredy.spring.actividad4.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Esta clase maneja las excepciones globales de la aplicación y devuelve respuestas adecuadas al cliente

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String campo = error.getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación en los datos de entrada");
        respuesta.put("detalles", errores);

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", ex.getMessage());

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}