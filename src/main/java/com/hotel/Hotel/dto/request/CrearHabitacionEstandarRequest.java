package com.hotel.Hotel.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la creación de una Habitación Estándar.
 * Usamos records para inmutabilidad.
 */
public record CrearHabitacionEstandarRequest(
        @NotBlank(message = "El número de habitación es obligatorio")
        String numero,

        @NotNull(message = "El precio por noche es obligatorio")
        @Min(value = 0, message = "El precio no puede ser negativo")
        Double precioPorNoche,

        @NotNull(message = "La capacidad máxima es obligatoria")
        @Min(value = 1, message = "La capacidad debe ser al menos 1")
        Integer capacidadMaxima,

        @NotNull(message = "El número de camas individuales es obligatorio")
        @Min(value = 1, message = "Debe tener al menos 1 cama")
        Integer camasIndividuales
) {}