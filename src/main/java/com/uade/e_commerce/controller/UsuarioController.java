package com.uade.e_commerce.controller;

import com.uade.e_commerce.dto.RegistroUsuarioRequest;
import com.uade.e_commerce.dto.UsuarioResponse;
import com.uade.e_commerce.model.Usuario;
import com.uade.e_commerce.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody RegistroUsuarioRequest datos) {
        Usuario usuario = usuarioService.registrar(datos);
        UsuarioResponse respuesta = new UsuarioResponse(
                usuario.getId(), usuario.getNombre(), usuario.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @ExceptionHandler(UsuarioService.EmailDuplicadoException.class)
    public ResponseEntity<Map<String, String>> emailDuplicado() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "El email ya esta registrado"));
    }

    @ExceptionHandler(UsuarioService.DatosInvalidosException.class)
    public ResponseEntity<Map<String, String>> datosInvalidos() {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Nombre, email y password son obligatorios"));
    }
}
