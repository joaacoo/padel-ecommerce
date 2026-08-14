package com.uade.e_commerce.service;

import com.uade.e_commerce.model.Producto;
import com.uade.e_commerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // GET /api/productos (Obtener todos)
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // GET /api/productos/{id} (Obtener por ID)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null); // retorna null si no existe
    }

    // POST /api/productos (Crear producto)
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }
}
