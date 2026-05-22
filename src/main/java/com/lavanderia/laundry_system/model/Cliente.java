package com.lavanderia.laundry_system.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true)
    private String dni;

    @Column
    private String celular;

    @Column(nullable = false)
    private Boolean estado = true;
}