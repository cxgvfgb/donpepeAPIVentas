package pe.donpepe.ventas.dto;

import java.math.BigDecimal;

public record VentaRequest(
        String nombre,
        String producto,
        Integer cantidad,
        BigDecimal precioUnitario,
        String metodoPago
) { }
