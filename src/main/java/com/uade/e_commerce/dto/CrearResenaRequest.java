package com.uade.e_commerce.dto;

import lombok.Data;

@Data
public class CrearResenaRequest {
    private Long usuarioId;
    private String comentario;
    private Integer puntuacion;
}
