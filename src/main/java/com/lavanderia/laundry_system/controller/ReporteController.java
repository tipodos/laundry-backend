package com.lavanderia.laundry_system.controller;

import com.lavanderia.laundry_system.model.Orden;
import com.lavanderia.laundry_system.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final OrdenRepository ordenRepository;

    @GetMapping("/ordenes")
    public ResponseEntity<Map<String, Object>> reporteOrdenes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {

        List<Orden> ordenes = ordenRepository.findByFechaRecepcionBetween(inicio, fin);

        BigDecimal total = ordenes.stream()
                .map(Orden::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal adelantos = ordenes.stream()
                .map(o -> o.getAdelanto() != null ? o.getAdelanto() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> reporte = new HashMap<>();
        reporte.put("ordenes", ordenes);
        reporte.put("totalOrdenes", ordenes.size());
        reporte.put("ingresoTotal", total);
        reporte.put("adelantosTotal", adelantos);
        reporte.put("saldoPendiente", total.subtract(adelantos));

        return ResponseEntity.ok(reporte);
    }
}