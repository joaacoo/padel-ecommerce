package com.uade.e_commerce.service;

import com.uade.e_commerce.dto.RegistroUsuarioRequest;
import com.uade.e_commerce.model.Usuario;
import com.uade.e_commerce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordService passwordService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordService = passwordService;
    }

    public Usuario registrar(RegistroUsuarioRequest datos) {
        validarCampos(datos);

        String email = datos.email().trim().toLowerCase();
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailDuplicadoException();
        }

        String passwordProtegida = passwordService.hashear(datos.password());
        Usuario usuario = new Usuario(datos.nombre().trim(), email, passwordProtegida);
        return usuarioRepository.save(usuario);
    }

    private void validarCampos(RegistroUsuarioRequest datos) {
        if (datos == null || estaVacio(datos.nombre()) || estaVacio(datos.email())
                || estaVacio(datos.password())) {
            throw new DatosInvalidosException();
        }
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.isBlank();
    }

    public static class EmailDuplicadoException extends RuntimeException {
    }

    public static class DatosInvalidosException extends RuntimeException {
    }
}
