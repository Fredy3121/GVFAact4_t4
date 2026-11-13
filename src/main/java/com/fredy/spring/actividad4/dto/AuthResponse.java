package com.fredy.spring.actividad4.dto;


public record AuthResponse(
    String token,
    String tipo,
    String email,
    String nombre
) {}