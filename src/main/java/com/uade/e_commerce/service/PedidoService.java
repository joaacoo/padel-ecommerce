package com.uade.e_commerce.service;

import com.uade.e_commerce.model.Pedido;
import com.uade.e_commerce.model.Usuario;
import com.uade.e_commerce.repository.PedidoRepository;
import com.uade.e_commerce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // GET /api/pedidos/{id}
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(PedidoNoEncontradoException::new);
    }

    // GET /api/users/{id}/pedidos
    public List<Pedido> obtenerPedidosDeUsuario(Long usuarioId) {

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new UsuarioNoEncontradoException();
        }

        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    // POST /api/pedidos
    public Pedido crearPedido(Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNoEncontradoException::new);

        Pedido pedido = new Pedido();

        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());

        // Esto se va a obtener del carrito
        // cuando Persona 3 termine su parte.
        pedido.setTotal(0.0);

        return pedidoRepository.save(pedido);
    }

    public static class PedidoNoEncontradoException extends RuntimeException {
    }

    public static class UsuarioNoEncontradoException extends RuntimeException {
    }
}
