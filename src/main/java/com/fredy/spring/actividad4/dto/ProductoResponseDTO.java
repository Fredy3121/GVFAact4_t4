package com.fredy.spring.actividad4.dto;


public record ProductoResponseDTO(
    Long id,
    String nombre,
    Double precio,
    Integer stock,
    Long categoriaId,
    String categoriaNombre
) {}