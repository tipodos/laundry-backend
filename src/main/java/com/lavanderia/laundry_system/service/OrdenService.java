package com.lavanderia.laundry_system.service;

import com.lavanderia.laundry_system.model.Orden;
import com.lavanderia.laundry_system.model.OrdenDetalle;
import com.lavanderia.laundry_system.repository.OrdenDetalleRepository;
import com.lavanderia.laundry_system.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final OrdenDetalleRepository ordenDetalleRepository;

    public List<Orden> listarTodos() {
        return ordenRepository.findAll();
    }

    public List<Orden> listarPorEstado(Integer estado) {
        return ordenRepository.findByEstado(estado);
    }

    public List<Orden> listarPorCliente(Integer clienteId) {
        return ordenRepository.findByClienteId(clienteId);
    }

    public Orden buscarPorId(Integer id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }

    @Transactional
    public Orden guardar(Orden orden, List<OrdenDetalle> detalles) {
        // Calcular total automáticamente
        BigDecimal total = detalles.stream()
                .map(OrdenDetalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orden.setTotal(total);
        orden.setFechaRecepcion(LocalDateTime.now());
        orden.setEstado(1); // 1=recibido

        Orden ordenGuardada = ordenRepository.save(orden);

        // Guardar cada detalle ligado a la orden
        detalles.forEach(detalle -> {
            detalle.setOrden(ordenGuardada);
            ordenDetalleRepository.save(detalle);
        });

        return ordenGuardada;
    }

    public Orden cambiarEstado(Integer id, Integer nuevoEstado) {
        Orden orden = buscarPorId(id);
        orden.setEstado(nuevoEstado);
        return ordenRepository.save(orden);
    }

    public List<OrdenDetalle> listarDetallesPorOrden(Integer ordenId) {
        return ordenDetalleRepository.findByOrdenId(ordenId);
    }
}