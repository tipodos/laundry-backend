package com.lavanderia.laundry_system.controller;

import com.lavanderia.laundry_system.model.Servicio;
import com.lavanderia.laundry_system.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<Servicio>> listar() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Servicio>> listarActivos() {
        return ResponseEntity.ok(servicioService.listarActivos());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Servicio>> listarPorCategoria(
            @PathVariable Integer categoriaId) {
        return ResponseEntity.ok(servicioService.listarPorCategoria(categoriaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Servicio> guardar(@RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.guardar(servicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Integer id,
                                               @RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.actualizar(id, servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}