package com.hotel.Hotel.service;

import com.hotel.Hotel.domain.Cliente;
import com.hotel.Hotel.dto.request.CrearClienteRequest;
import com.hotel.Hotel.dto.response.ClienteResponse;
import com.hotel.Hotel.dto.response.ClienteResumenResponse;
import com.hotel.Hotel.mapper.ClienteMapper;
import com.hotel.Hotel.repository.ClienteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hotel.Hotel.dto.request.ActualizarClienteRequest;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Transactional
    public ClienteResponse crear(CrearClienteRequest request) {
        Cliente cliente = new Cliente(request.nombre(), request.email());
        Cliente guardado = clienteRepository.save(cliente);
        return clienteMapper.toResponseList(List.of(guardado)).get(0);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listarTodos() {
        return clienteMapper.toResponseList(clienteRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorId(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        return clienteMapper.toResponseList(List.of(cliente)).get(0);
    }



    @Transactional(readOnly = true)
    public ClienteResumenResponse obtenerResumenPorId(UUID id) {
        // 1. Buscamos el cliente en la base de datos
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        
        // 2. Usamos el mapper para transformarlo al DTO ejecutivo (con totales y lista anidada)
        return clienteMapper.toResumenResponse(cliente);
}
/**
     * Requisito: Modificar selectivamente nombre o email sin alterar el resto.
     * PATCH /api/clientes/{id}
     */
    @Transactional
    public ClienteResponse actualizarParcial(UUID id, ActualizarClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));

        if (request.nombre() != null) {
            cliente.setNombre(request.nombre());
        }
        if (request.email() != null) {
            cliente.setEmail(request.email());
        }

        Cliente actualizado = clienteRepository.save(cliente);
        return clienteMapper.toResponseList(List.of(actualizado)).get(0);
    }
}