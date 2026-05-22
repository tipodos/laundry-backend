package com.lavanderia.laundry_system.repository;

import com.lavanderia.laundry_system.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {

    List<Orden> findByClienteId(Integer clienteId);
    List<Orden> findByEstado(Integer estado);

    @Query("SELECT COUNT(o) FROM Orden o WHERE o.estado = :estado")
    Long contarPorEstado(@Param("estado") Integer estado);

    @Query("SELECT SUM(o.total) FROM Orden o WHERE o.estado = :estado")
    BigDecimal sumaTotalPorEstado(@Param("estado") Integer estado);

    @Query("SELECT SUM(o.total) FROM Orden o WHERE o.estado != :estado")
    BigDecimal sumaTotalPorEstadoNot(@Param("estado") Integer estado);

    @Query("SELECT SUM(o.adelanto) FROM Orden o WHERE o.estado != :estado")
    BigDecimal sumaAdelantosPorEstadoNot(@Param("estado") Integer estado);
    boolean existsByClienteId(Integer clienteId);
    @Query("SELECT COUNT(o) > 0 FROM Orden o WHERE o.user.id = :userId")
    boolean existsByUserId(@Param("userId") Integer userId);
    @Query("SELECT o FROM Orden o WHERE o.fechaRecepcion BETWEEN :inicio AND :fin")
    List<Orden> findByFechaRecepcionBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );
}