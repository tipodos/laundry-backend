package com.lavanderia.laundry_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String usuario;
    private String nombre;
    private String role;
}