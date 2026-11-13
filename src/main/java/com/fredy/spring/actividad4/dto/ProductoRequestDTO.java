package com.fredy.spring.actividad4.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
// Este DTO se utiliza para recibir los datos de un producto desde el cliente, y se valida que los campos cumplan con ciertas condiciones

public record ProductoRequestDTO(
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    String nombre,

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    Double precio,

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    Integer stock,

    @NotNull(message = "El ID de categoría es obligatorio")
    Long categoriaId
) {}