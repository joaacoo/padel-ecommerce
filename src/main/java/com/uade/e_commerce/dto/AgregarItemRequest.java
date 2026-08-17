package com.uade.e_commerce.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AgregarItemRequest {
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}