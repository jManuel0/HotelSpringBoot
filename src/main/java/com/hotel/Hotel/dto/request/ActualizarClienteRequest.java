package com.hotel.Hotel.dto.request;

/**
 * Los campos son opcionales. Si envían null, no se actualizará ese campo.
 */
public record ActualizarClienteRequest(
        String nombre,
        String email
) {}
