package com.fredy.spring.actividad4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fredy.spring.actividad4.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}