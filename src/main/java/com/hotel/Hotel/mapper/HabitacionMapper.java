package com.hotel.Hotel.mapper;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.request.CrearHabitacionEstandarRequest;
import com.hotel.Hotel.dto.request.CrearSuitePresidencialRequest;
import com.hotel.Hotel.dto.response.HabitacionEstandarResponse;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.dto.response.SuitePresidencialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {

    /**
     * Mapeo Polimórfico (Herencia)
     * Dependiendo de la instancia real que llegue (Estandar o Suite), 
     * MapStruct delegará al método correspondiente.
     */
    default HabitacionResponse toResponse(Habitacion habitacion) {
        if (habitacion == null) {
            return null;
        }
        if (habitacion instanceof HabitacionEstandar estandar) {
            return toEstandarResponse(estandar);
        }
        if (habitacion instanceof SuitePresidencial suite) {
            return toSuiteResponse(suite);
        }
        throw new IllegalArgumentException(
                "Tipo de habitación no soportado: " + habitacion.getClass().getName());
    }

    // MapStruct necesita saber cómo mapear cada clase hija de forma individual
    HabitacionEstandarResponse toEstandarResponse(HabitacionEstandar habitacion);
    SuitePresidencialResponse toSuiteResponse(SuitePresidencial habitacion);

    // ----------------------------------------------------
    // Métodos para convertir de Request a Entity (Creación)
    // ----------------------------------------------------
    
    HabitacionEstandar toEntity(CrearHabitacionEstandarRequest request);
    
    @Mapping(target = "mayordomo", source = "incluyeMayordomo")
    @Mapping(target = "jacuzzi", source = "jacuzziPrivado")
    SuitePresidencial toEntity(CrearSuitePresidencialRequest request);
}