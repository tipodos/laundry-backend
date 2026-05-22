package com.lavanderia.laundry_system.repository;

import com.lavanderia.laundry_system.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}