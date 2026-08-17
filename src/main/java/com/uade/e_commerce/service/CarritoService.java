package com.uade.e_commerce.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.uade.e_commerce.model.Carrito;
import com.uade.e_commerce.model.ItemCarrito;
import com.uade.e_commerce.repository.CarritoRepository;
import com.uade.e_commerce.repository.ItemCarritoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CarritoService {
    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;

    public Carrito getCarritoByUsuarioId(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> createCarrito(usuarioId));
    }

    private Carrito createCarrito(Long usuarioId) {
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(usuarioId);
        return carritoRepository.save(carrito);
    }

    public List<ItemCarrito> getAllItemCarritos(Long usuarioId) {
        Carrito carrito = getCarritoByUsuarioId(usuarioId);
        return itemCarritoRepository.findByCarritoId(carrito.getId());
    }

    public ItemCarrito addItemCarrito(Long usuarioId, Long productoId, Integer cantidad, BigDecimal precioUnitario) {

        Carrito carrito = getCarritoByUsuarioId(usuarioId);

        Optional<ItemCarrito> existente = itemCarritoRepository.findByCarritoIdAndProductoId(carrito.getId(),
                productoId);

        if (existente.isPresent()) {
            ItemCarrito item = existente.get();
            item.setCantidad(item.getCantidad() + cantidad); // ← SUMA
            return itemCarritoRepository.save(item);
        }

        ItemCarrito nuevo = new ItemCarrito();
        nuevo.setCarritoId(carrito.getId());
        nuevo.setProductoId(productoId);
        nuevo.setCantidad(cantidad);
        nuevo.setPrecioUnitario(precioUnitario);
        return itemCarritoRepository.save(nuevo);
    }

    public ItemCarrito actualizarCantidad(Long itemId, Integer cantidad) {
        Optional<ItemCarrito> existente = itemCarritoRepository.findById(itemId);
        if (existente.isPresent()) {
            ItemCarrito item = existente.get();
            item.setCantidad(cantidad);
            return itemCarritoRepository.save(item);
        }
        throw new RuntimeException("Item no encontrado");
    }

    public void eliminarItem(Long usuarioId, Long productoId) {
        Carrito carrito = getCarritoByUsuarioId(usuarioId);
        Optional<ItemCarrito> existente = itemCarritoRepository.findByCarritoIdAndProductoId(carrito.getId(),
                productoId);

        existente.ifPresent(itemCarritoRepository::delete);
    }

    @Transactional
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = getCarritoByUsuarioId(usuarioId);
        List<ItemCarrito> items = itemCarritoRepository.findByCarritoId(carrito.getId());
        itemCarritoRepository.deleteAll(items);
    }

}