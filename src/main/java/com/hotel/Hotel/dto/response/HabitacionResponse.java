package com.hotel.Hotel.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.UUID;

/**
 * Interfaz base para las respuestas polimórficas de Habitaciones.
 * @JsonTypeInfo configura a Jackson para que inyecte una propiedad "tipo" en el JSON final.
 * @JsonSubTypes indica cuáles son las clases hijas válidas y qué valor tendrá el "tipo".
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo" // Nombre del campo discriminador en el JSON
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HabitacionEstandarResponse.class, name = "ESTANDAR"),
        @JsonSubTypes.Type(value = SuitePresidencialResponse.class, name = "SUITE")
})
public sealed interface HabitacionResponse permits HabitacionEstandarResponse, SuitePresidencialResponse {
    // Métodos comunes (opcional, pero buena práctica para exponer getters comunes si fuera necesario)
    UUID id();
    String numero();
    Double precioPorNoche();
    Integer capacidadMaxima();
}