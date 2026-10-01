package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.domain.Reserva;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.dto.response.ReservaItemResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    /**
     * Mapea un Cliente al DTO de Resumen Ejecutivo.
     * Calcula totalReservasRealizadas contando el tamaño de la lista.
     * Usa un método @Named para calcular el monto total.
     * Mapea la lista de 'reservas' hacia 'reservasRecientes' (MapStruct usará toReservaItemResponse automáticamente).
     */
    @Mapping(target = "totalReservasRealizadas", expression = "java(cliente.getReservas() != null ? cliente.getReservas().size() : 0)")
    @Mapping(target = "montoTotalGastado", source = "reservas", qualifiedByName = "calcularMonto")
    @Mapping(target = "reservasRecientes", source = "reservas")
    ClienteResumenResponse toResumenResponse(Cliente cliente);

    /**
     * Mapea una Reserva individual al Sub-DTO.
     * Extrae el número de habitación navegando por las entidades: reserva -> habitacion -> numero.
     */
    @Mapping(target = "numeroHabitacion", source = "habitacion.numero")
    ReservaItemResponse toReservaItemResponse(Reserva reserva);

    /**
     * Método auxiliar para calcular la sumatoria usando Java Streams.
     */
    @Named("calcularMonto")
    default double calcularMontoTotalGastado(List<Reserva> reservas) {
        if (reservas == null || reservas.isEmpty()) {
            return 0.0;
        }
        return reservas.stream()
                .filter(r -> r != null)
                .mapToDouble(Reserva::getCostoTotal)
                .sum();
    }

    List<ClienteResponse> toResponseList(List<Cliente> all);

     /**
     * Mapeo para actualización parcial.
     * Si el DTO trae un null, MapStruct ignorará ese campo y no sobrescribirá la entidad.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)              // Protegemos el ID
    @Mapping(target = "activo", ignore = true)          // Protegemos el estado activo
    @Mapping(target = "penalizaciones", ignore = true)  // Protegemos las penalizaciones
    @Mapping(target = "reservas", ignore = true)        // Protegemos la lista de reservas
    void updateClienteFromDto(ActualizarClienteRequest dto, @MappingTarget Cliente entity);
}

