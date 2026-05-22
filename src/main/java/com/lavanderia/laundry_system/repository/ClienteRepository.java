package com.lavanderia.laundry_system.repository;

import com.lavanderia.laundry_system.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByDni(String dni);
}