package com.lavanderia.laundry_system.controller;

import com.lavanderia.laundry_system.model.User;
import com.lavanderia.laundry_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listar() {
        return ResponseEntity.ok(userService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<User> guardar(@RequestBody User user) {
        return ResponseEntity.ok(userService.guardar(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> actualizar(@PathVariable Integer id,
                                           @RequestBody User user) {
        return ResponseEntity.ok(userService.actualizar(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        userService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}