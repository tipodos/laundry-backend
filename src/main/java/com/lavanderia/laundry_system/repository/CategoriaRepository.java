package com.lavanderia.laundry_system.repository;

import com.lavanderia.laundry_system.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByEstadoTrue();
}