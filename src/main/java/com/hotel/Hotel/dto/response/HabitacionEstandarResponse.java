package com.hotel.Hotel.dto.response;

import java.util.UUID;

/**
 * Respuesta específica para Habitación Estándar.
 */
public record HabitacionEstandarResponse(
        UUID id,
        String numero,
        Double precioPorNoche,
        Integer capacidadMaxima,
        Integer camasIndividuales
) implements HabitacionResponse {}