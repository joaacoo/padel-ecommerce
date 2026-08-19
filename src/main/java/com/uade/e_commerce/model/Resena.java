package com.uade.e_commerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resenas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId; // a que producto pertenece la reseña
    private Long usuarioId; // quien la escribió
    private String comentario; // "Excelente paleta, buen control"
    private Integer puntuacion; // 1 a 5
}
