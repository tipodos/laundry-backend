package com.lavanderia.laundry_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lavanderia.laundry_system.model.Orden;
import com.lavanderia.laundry_system.model.OrdenDetalle;
import com.lavanderia.laundry_system.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<Orden>> listar() {
        return ResponseEntity.ok(ordenService.listarTodos());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Orden>> listarPorEstado(@PathVariable Integer estado) {
        return ResponseEntity.ok(ordenService.listarPorEstado(estado));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Orden>> listarPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(ordenService.listarPorCliente(clienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ordenService.buscarPorId(id));
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<OrdenDetalle>> listarDetalles(@PathVariable Integer id) {
        return ResponseEntity.ok(ordenService.listarDetallesPorOrden(id));
    }

    @PostMapping
    public ResponseEntity<Orden> guardar(@RequestBody Map<String, Object> request) {
        try {
            // Extraer orden
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            Orden orden = mapper.convertValue(request.get("orden"), Orden.class);

            List<OrdenDetalle> detalles = mapper.convertValue(
                    request.get("detalles"),
                    mapper.getTypeFactory().constructCollectionType(List.class, OrdenDetalle.class)
            );

            return ResponseEntity.ok(ordenService.guardar(orden, detalles));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Orden> cambiarEstado(@PathVariable Integer id,
                                               @RequestParam Integer estado) {
        return ResponseEntity.ok(ordenService.cambiarEstado(id, estado));
    }
}