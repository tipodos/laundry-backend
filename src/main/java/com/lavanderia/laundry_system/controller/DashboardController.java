package com.lavanderia.laundry_system.controller;

import com.lavanderia.laundry_system.model.Orden;
import com.lavanderia.laundry_system.repository.ClienteRepository;
import com.lavanderia.laundry_system.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final OrdenRepository ordenRepository;
    private final ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();

        // Contadores
        stats.put("totalClientes", clienteRepository.count());
        stats.put("ordenesRecibidas", ordenRepository.contarPorEstado(1));
        stats.put("ordenesProceso", ordenRepository.contarPorEstado(2));
        stats.put("ordenesListas", ordenRepository.contarPorEstado(3));
        stats.put("ordenesEntregadas", ordenRepository.contarPorEstado(4));
        stats.put("totalOrdenes", ordenRepository.count());

        // Ingresos
        var ingresoEntregadas = ordenRepository.sumaTotalPorEstado(4);
        var totalPendiente = ordenRepository.sumaTotalPorEstadoNot(4);
        var adelantosPendiente = ordenRepository.sumaAdelantosPorEstadoNot(4);

        stats.put("ingresoTotal", ingresoEntregadas != null ? ingresoEntregadas : 0);
        stats.put("saldoPendiente", calcularSaldo(totalPendiente, adelantosPendiente));

        // Últimas 5 órdenes
        List<Orden> ultimasOrdenes = ordenRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .limit(5)
                .collect(Collectors.toList());
        stats.put("ultimasOrdenes", ultimasOrdenes);

        // Órdenes por día últimos 7 días
        List<Map<String, Object>> ordenesPorDia = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime inicio = LocalDateTime.now().minusDays(i).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime fin = LocalDateTime.now().minusDays(i).withHour(23).withMinute(59).withSecond(59);
            List<Orden> ordenesDia = ordenRepository.findByFechaRecepcionBetween(inicio, fin);

            Map<String, Object> dia = new HashMap<>();
            dia.put("dia", inicio.getDayOfMonth() + "/" + inicio.getMonthValue());
            dia.put("cantidad", ordenesDia.size());
            dia.put("ingresos", ordenesDia.stream()
                    .mapToDouble(o -> o.getTotal().doubleValue())
                    .sum());
            ordenesPorDia.add(dia);
        }
        stats.put("ordenesPorDia", ordenesPorDia);

        return ResponseEntity.ok(stats);
    }

    private Object calcularSaldo(Object total, Object adelantos) {
        double t = total != null ? Double.parseDouble(total.toString()) : 0;
        double a = adelantos != null ? Double.parseDouble(adelantos.toString()) : 0;
        return t - a;
    }
}