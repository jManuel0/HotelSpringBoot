package com.hotel.Hotel.controller;

import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.dto.request.CrearHabitacionEstandarRequest;
import com.hotel.Hotel.dto.request.CrearSuitePresidencialRequest;
import com.hotel.Hotel.dto.response.HabitacionResponse;
import com.hotel.Hotel.mapper.HabitacionMapper;
import com.hotel.Hotel.service.HabitacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;
    private final HabitacionMapper habitacionMapper;

    public HabitacionController(HabitacionService habitacionService, HabitacionMapper habitacionMapper) {
        this.habitacionService = habitacionService;
        this.habitacionMapper = habitacionMapper;
    }

    /**
     * Requisito: Retorna una lista polimórfica homogénea en JSON.
     * Jackson se encargará de agregar el campo "tipo" automáticamente.
     */
    @GetMapping
    public ResponseEntity<List<HabitacionResponse>> listarHabitaciones() {
        // Obtenemos todas las entidades y usamos un Stream para mapearlas usando MapStruct
        List<HabitacionResponse> respuestas = habitacionService.obtenerTodas().stream()
                .map(habitacionMapper::toResponse)
                .toList();
        
        return ResponseEntity.ok(respuestas);
    }

    /**
     * Requisito: Endpoint para crear Suite Presidencial
     */
    @PostMapping("/suites")
    public ResponseEntity<HabitacionResponse> crearSuite(@Valid @RequestBody CrearSuitePresidencialRequest request) {
        SuitePresidencial entidad = habitacionMapper.toEntity(request);
        SuitePresidencial guardada = habitacionService.guardarSuite(entidad);
        
        // Devolvemos 201 Created y el DTO mapeado
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionMapper.toResponse(guardada));
    }

    /**
     * (Opcional pero recomendado) Endpoint para crear Estandar
     */
    @PostMapping("/estandares")
    public ResponseEntity<HabitacionResponse> crearEstandar(@Valid @RequestBody CrearHabitacionEstandarRequest request) {
        HabitacionEstandar entidad = habitacionMapper.toEntity(request);
        HabitacionEstandar guardada = habitacionService.guardarEstandar(entidad);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionMapper.toResponse(guardada));
    }
}