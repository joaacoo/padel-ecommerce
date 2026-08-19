package com.uade.e_commerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uade.e_commerce.dto.CrearResenaRequest;
import com.uade.e_commerce.model.Resena;
import com.uade.e_commerce.service.ResenaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    // POST /api/productos/{id}/resenas -> Crear reseña
    @PostMapping("/api/productos/{id}/resenas")
    public ResponseEntity<Resena> crearResena(@PathVariable Long id, @RequestBody CrearResenaRequest request) {
        Resena nueva = resenaService.crearResena(id, request.getUsuarioId(), request.getComentario(),
                request.getPuntuacion());
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // GET /api/productos/{id}/resenas -> Ver reseñas de un producto
    @GetMapping("/api/productos/{id}/resenas")
    public ResponseEntity<List<Resena>> verResenas(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.obtenerPorProducto(id));
    }

    // DELETE /api/resenas/{id} -> Eliminar reseña
    @DeleteMapping("/api/resenas/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        resenaService.eliminarResena(id);
        return ResponseEntity.noContent().build();
    }
}
