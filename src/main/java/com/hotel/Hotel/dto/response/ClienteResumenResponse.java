package com.hotel.Hotel.dto.response;

import java.util.List;
import java.util.UUID;

/**
 * DTO Compuesto para la vista ejecutiva de recepción.
 */
public record ClienteResumenResponse(
        UUID id,
        String nombre,
        String email,
        boolean activo,
        int penalizaciones,
        int totalReservasRealizadas,
        double montoTotalGastado,
        List<ReservaItemResponse> reservasRecientes
) {}