package pe.donpepe.ventas.dto;

import java.math.BigDecimal;

public record VentaRequest(
        Integer cantidad,
        BigDecimal precioUnitario,
        String metodoPago
) { }
