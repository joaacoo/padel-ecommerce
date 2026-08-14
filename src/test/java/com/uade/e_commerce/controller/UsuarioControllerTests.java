package com.uade.e_commerce.controller;

import tools.jackson.databind.ObjectMapper;
import com.uade.e_commerce.model.Usuario;
import com.uade.e_commerce.repository.UsuarioRepository;
import com.uade.e_commerce.service.PasswordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordService passwordService;

    @Test
    void registraUnUsuarioSinDevolverLaPassword() throws Exception {
        Map<String, String> datos = Map.of(
                "nombre", "Lucas",
                "email", "lucas@example.com",
                "password", "123456");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Lucas"))
                .andExpect(jsonPath("$.email").value("lucas@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist());

        Usuario usuarioGuardado = usuarioRepository.findByEmailIgnoreCase("lucas@example.com")
                .orElseThrow();
        org.junit.jupiter.api.Assertions.assertNotEquals("123456", usuarioGuardado.getPassword());
        org.junit.jupiter.api.Assertions.assertTrue(
                passwordService.coincide("123456", usuarioGuardado.getPassword()));
    }

    @Test
    void rechazaCamposVacios() throws Exception {
        Map<String, String> datos = Map.of(
                "nombre", "",
                "email", "otro@example.com",
                "password", "123456");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(datos)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rechazaUnEmailDuplicado() throws Exception {
        Map<String, String> datos = Map.of(
                "nombre", "Ana",
                "email", "ana@example.com",
                "password", "123456");
        String cuerpo = objectMapper.writeValueAsString(datos);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuerpo))
                .andExpect(status().isConflict());
    }
}
