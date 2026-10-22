package pe.donpepe.ventas.dto;

import java.math.BigDecimal;

// Datos que entran a la API.
public record VentaRequest(
        String nombre,
        String producto,
        Integer cantidad,
        BigDecimal precioUnitario,
        String metodoPago
) { }
