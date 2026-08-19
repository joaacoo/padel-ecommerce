package com.uade.e_commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uade.e_commerce.model.Resena;
import com.uade.e_commerce.repository.ResenaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository resenaRepository;

    // POST /api/productos/{id}/resenas
    public Resena crearResena(Long productoId, Long usuarioId, String comentario, Integer puntuacion) {
        Resena resena = new Resena();
        resena.setProductoId(productoId);
        resena.setUsuarioId(usuarioId);
        resena.setComentario(comentario);
        resena.setPuntuacion(puntuacion);
        return resenaRepository.save(resena);
    }

    // GET /api/productos/{id}/resenas
    public List<Resena> obtenerPorProducto(Long productoId) {
        return resenaRepository.findByProductoId(productoId);
    }

    // DELETE /api/resenas/{id}
    public void eliminarResena(Long id) {
        resenaRepository.deleteById(id);
    }
}
