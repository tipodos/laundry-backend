package com.lavanderia.laundry_system.service;

import com.lavanderia.laundry_system.model.User;
import com.lavanderia.laundry_system.repository.OrdenRepository;
import com.lavanderia.laundry_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrdenRepository ordenRepository;

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    public User buscarPorId(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public User guardar(User user) {
        if (userRepository.existsByUsuario(user.getUsuario())) {
            throw new RuntimeException("Ya existe un usuario con ese nombre");
        }
        // Encriptar password antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User actualizar(Integer id, User userNuevo) {
        User userExistente = buscarPorId(id);
        userExistente.setNombre(userNuevo.getNombre());
        userExistente.setEstado(userNuevo.getEstado());
        userExistente.setRole(userNuevo.getRole());
        return userRepository.save(userExistente);
    }

    public void eliminar(Integer id) {
        User user = buscarPorId(id);

        // No eliminar si tiene órdenes
        if (ordenRepository.existsByUserId(id)) {
            throw new RuntimeException("No se puede eliminar, el usuario tiene órdenes registradas");
        }

        // No eliminar al último admin
        if (user.getRole().getRole().equals("ADMIN")) {
            long totalAdmins = userRepository.contarPorRole("ADMIN");
            if (totalAdmins <= 1) {
                throw new RuntimeException("No se puede eliminar el único administrador");
            }
        }

        userRepository.deleteById(id);
    }
}