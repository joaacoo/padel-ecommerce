package com.uade.e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.e_commerce.model.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByProductoId(Long productoId);
}
