package com.lavanderia.laundry_system.controller;

import com.lavanderia.laundry_system.model.Role;
import com.lavanderia.laundry_system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> listar() {
        return ResponseEntity.ok(roleService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Role> guardar(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.guardar(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        roleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}