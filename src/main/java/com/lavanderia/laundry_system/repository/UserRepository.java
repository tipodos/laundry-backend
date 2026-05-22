package com.lavanderia.laundry_system.repository;

import com.lavanderia.laundry_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsuario(String usuario);
    boolean existsByUsuario(String usuario);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role.role = :role")
    long contarPorRole(@Param("role") String role);
}