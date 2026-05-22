package com.lavanderia.laundry_system.service;

import com.lavanderia.laundry_system.model.Role;
import com.lavanderia.laundry_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> listarTodos() {
        return roleRepository.findAll();
    }

    public Role guardar(Role role) {
        return roleRepository.save(role);
    }

    public Role buscarPorId(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    public void eliminar(Integer id) {
        roleRepository.deleteById(id);
    }
}