package com.lavanderia.laundry_system.service;

import com.lavanderia.laundry_system.model.Servicio;
import com.lavanderia.laundry_system.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public List<Servicio> listarActivos() {
        return servicioRepository.findByEstadoTrue();
    }

    public List<Servicio> listarPorCategoria(Integer categoriaId) {
        return servicioRepository.findByCategoriaId(categoriaId);
    }

    public Servicio buscarPorId(Integer id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Integer id, Servicio servicioNuevo) {
        Servicio servicioExistente = buscarPorId(id);
        servicioExistente.setNombre(servicioNuevo.getNombre());
        servicioExistente.setPrecio(servicioNuevo.getPrecio());
        servicioExistente.setEstado(servicioNuevo.getEstado());
        servicioExistente.setCategoria(servicioNuevo.getCategoria());
        return servicioRepository.save(servicioExistente);
    }

    public void eliminar(Integer id) {
        servicioRepository.deleteById(id);
    }
}