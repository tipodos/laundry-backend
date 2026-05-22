package com.lavanderia.laundry_system.service;

import com.lavanderia.laundry_system.model.Categoria;
import com.lavanderia.laundry_system.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarActivos() {
        return categoriaRepository.findByEstadoTrue();
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizar(Integer id, Categoria categoriaNueva) {
        Categoria categoriaExistente = buscarPorId(id);
        categoriaExistente.setCategoria(categoriaNueva.getCategoria());
        categoriaExistente.setEstado(categoriaNueva.getEstado());
        return categoriaRepository.save(categoriaExistente);
    }

    public void eliminar(Integer id) {
        categoriaRepository.deleteById(id);
    }
}