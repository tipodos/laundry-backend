package com.lavanderia.laundry_system.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String usuario;
    private String password;
}