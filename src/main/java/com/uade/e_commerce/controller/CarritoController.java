package com.uade.e_commerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.uade.e_commerce.dto.ActualizarCantidadRequest;
import com.uade.e_commerce.dto.AgregarItemRequest;
import com.uade.e_commerce.model.ItemCarrito;
import com.uade.e_commerce.service.CarritoService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor

public class CarritoController {
    private final CarritoService carritoService;

    @GetMapping
    public ResponseEntity<List<ItemCarrito>> verCarrito(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(carritoService.getAllItemCarritos(usuarioId));
    }

    @PostMapping("/productos")
    public ResponseEntity<ItemCarrito> agregarItem(@RequestParam Long usuarioId,
            @RequestBody AgregarItemRequest request) {
        ItemCarrito item = carritoService.addItemCarrito(
                usuarioId, request.getProductoId(),
                request.getCantidad(), request.getPrecioUnitario());
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<ItemCarrito> actualizar(@PathVariable Long itemId,
            @RequestBody ActualizarCantidadRequest request) {
        return ResponseEntity.ok(carritoService.actualizarCantidad(itemId, request.getCantidad()));
    }

    @DeleteMapping("/productos/{productoId}")
    public ResponseEntity<Void> eliminar(@RequestParam Long usuarioId, @PathVariable Long productoId) {
        carritoService.eliminarItem(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> vaciar(@RequestParam Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
