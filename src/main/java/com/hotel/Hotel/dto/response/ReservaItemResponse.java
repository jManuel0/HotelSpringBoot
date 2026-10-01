package com.hotel.Hotel.dto.response;

import com.hotel.Hotel.domain.EstadoReserva;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Sub-DTO limpio. Contiene sólo los datos de la reserva y la habitación,
 * pero SIN hacer referencia de vuelta al Cliente para evitar bucles.
 */
public record ReservaItemResponse(
        UUID id,
        String numeroHabitacion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        EstadoReserva estado,
        Double costoTotal
) {}