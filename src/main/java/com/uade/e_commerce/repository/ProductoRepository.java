package com.uade.e_commerce.repository;

import com.uade.e_commerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// al heredar de JpaRepository, ya tienes listos metodos como save(), findAll()
// y findById()
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
