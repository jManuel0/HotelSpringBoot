package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.*;
import com.hotel.Hotel.dto.response.ReservaResponse;
import com.hotel.Hotel.mapper.ReservaMapper;
import com.hotel.Hotel.repository.ClienteRepository;
import com.hotel.Hotel.repository.HabitacionRepository;
import com.hotel.Hotel.repository.ReservaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private HabitacionRepository habitacionRepository;

    @Mock
    private ReservaMapper reservaMapper;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debe confirmar exitosamente cuando la reserva existe y está PENDIENTE")
    void debeConfirmarReservaExitosamente() {
        UUID reservaId = UUID.randomUUID();
        Cliente cliente = new Cliente("Beatriz Morales", "beatriz@empresa.com");
        Habitacion habitacion = new HabitacionEstandar("201", 2, 200.0, 1);
        RangoFechas fechas = new RangoFechas(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3));
        Reserva reserva = new Reserva(cliente, habitacion, fechas);

        ReservaResponse respuestaEsperada = new ReservaResponse(
                reservaId,
                "Beatriz Morales",
                "201",
                fechas.fechaInicio(),
                fechas.fechaFin(),
                400.0,
                "CONFIRMADA");

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reservaMapper.toResponse(any(Reserva.class))).thenReturn(respuestaEsperada);

        ReservaResponse resultado = reservaService.confirmar(reservaId);

        assertNotNull(resultado);
        assertEquals("CONFIRMADA", resultado.estado());
        assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        verify(reservaRepository).findById(reservaId);
        verify(reservaRepository).save(reserva);
        verify(reservaMapper).toResponse(reserva);
    }

    // Prueba 1
    @Test
    @DisplayName("Debe cancelar una reserva existente")
    void debeCancelarReserva() {
        Reserva reserva = crearReserva();
        ReservaResponse respuesta = crearRespuesta(reserva, "CANCELADA");
        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        when(reservaMapper.toResponse(reserva)).thenReturn(respuesta);

        ReservaResponse resultado = reservaService.cancelar(reserva.getId());

        assertEquals("CANCELADA", resultado.estado());
        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
    }

    // Prueba 2
    @Test
    @DisplayName("Debe obtener una reserva por ID")
    void debeObtenerReservaPorId() {
        Reserva reserva = crearReserva();
        ReservaResponse respuesta = crearRespuesta(reserva, "PENDIENTE");
        when(reservaRepository.findById(reserva.getId())).thenReturn(Optional.of(reserva));
        when(reservaMapper.toResponse(reserva)).thenReturn(respuesta);

        ReservaResponse resultado = reservaService.obtenerPorId(reserva.getId());

        assertSame(respuesta, resultado);
    }

    // Prueba 3
    @Test
    @DisplayName("Debe listar todas las reservas")
    void debeListarReservas() {
        Reserva reserva = crearReserva();
        ReservaResponse respuesta = crearRespuesta(reserva, "PENDIENTE");
        when(reservaRepository.findAll()).thenReturn(List.of(reserva));
        when(reservaMapper.toResponseList(List.of(reserva))).thenReturn(List.of(respuesta));

        List<ReservaResponse> resultado = reservaService.listarTodas();

        assertEquals(1, resultado.size());
        assertSame(respuesta, resultado.get(0));
    }

    // Prueba 4
    @Test
    @DisplayName("Debe lanzar una excepción si la reserva no existe")
    void debeLanzarExcepcionSiLaReservaNoExiste() {
        UUID id = UUID.randomUUID();
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> reservaService.obtenerPorId(id));
    }

    // Prueba 5
    @Test
    @DisplayName("Debe lanzar una excepción al confirmar una reserva inexistente")
    void debeLanzarExcepcionAlConfirmarReservaInexistente() {
        UUID id = UUID.randomUUID();
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> reservaService.confirmar(id));
    }

    private Reserva crearReserva() {
        Cliente cliente = new Cliente("Beatriz Morales", "beatriz@empresa.com");
        Habitacion habitacion = new HabitacionEstandar("202", 2, 200.0, 1);
        RangoFechas fechas = new RangoFechas(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2));
        return new Reserva(cliente, habitacion, fechas);
    }

    private ReservaResponse crearRespuesta(Reserva reserva, String estado) {
        return new ReservaResponse(
                reserva.getId(),
                "Beatriz Morales",
                "202",
                reserva.getPeriodo().fechaInicio(),
                reserva.getPeriodo().fechaFin(),
                reserva.getCostoTotal(),
                estado);
    }
}
