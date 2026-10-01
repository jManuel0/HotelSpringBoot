package com.hotel.Hotel.dto.response;

import java.util.UUID;

/**
 * Respuesta específica para Suite Presidencial.
 */
public record SuitePresidencialResponse(
        UUID id,
        String numero,
        Double precioPorNoche,
        Integer capacidadMaxima,
        Boolean incluyeMayordomo,
        Boolean jacuzziPrivado
) implements HabitacionResponse {}
