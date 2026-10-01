package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Habitacion;
import com.hotel.Hotel.domain.HabitacionEstandar;
import com.hotel.Hotel.domain.SuitePresidencial;
import com.hotel.Hotel.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    // Inyección de dependencias por constructor (buena práctica)
    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    @Transactional(readOnly = true)
    public List<Habitacion> obtenerTodas() {
        // Al usar findAll(), JPA y Hibernate son lo suficientemente inteligentes 
        // para traer tanto Estandares como Suites debido al InheritanceType.JOINED
        return habitacionRepository.findAll();
    }

    @Transactional
    public HabitacionEstandar guardarEstandar(HabitacionEstandar habitacion) {
        return habitacionRepository.save(habitacion);
    }

    @Transactional
    public SuitePresidencial guardarSuite(SuitePresidencial habitacion) {
        return habitacionRepository.save(habitacion);
    }
}