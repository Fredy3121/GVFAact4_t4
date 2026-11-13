package com.fredy.spring.actividad4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fredy.spring.actividad4.entity.Usuario;
//este es el repositorio de usuarios, donde se definen los metodos para acceder a la base de datos

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}