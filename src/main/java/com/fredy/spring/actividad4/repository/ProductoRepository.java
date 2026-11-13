package com.fredy.spring.actividad4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fredy.spring.actividad4.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}