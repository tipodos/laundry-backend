package com.lavanderia.laundry_system.repository;

import com.lavanderia.laundry_system.model.OrdenDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdenDetalleRepository extends JpaRepository<OrdenDetalle, Integer> {
    List<OrdenDetalle> findByOrdenId(Integer ordenId);
}