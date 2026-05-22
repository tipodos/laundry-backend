package com.lavanderia.laundry_system.controller;

import com.lavanderia.laundry_system.dto.LoginRequest;
import com.lavanderia.laundry_system.dto.LoginResponse;
import com.lavanderia.laundry_system.model.User;
import com.lavanderia.laundry_system.repository.UserRepository;
import com.lavanderia.laundry_system.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsuario(request.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Password incorrecto");
        }

        if (!user.getEstado()) {
            throw new RuntimeException("Usuario inactivo");
        }

        String token = jwtUtil.generarToken(user.getUsuario());

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getUsuario(),
                user.getNombre(),
                user.getRole().getRole()
        ));
    }
}