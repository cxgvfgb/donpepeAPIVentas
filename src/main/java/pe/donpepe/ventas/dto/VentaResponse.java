package pe.donpepe.ventas.dto;

import java.math.BigDecimal;

public record VentaResponse(
        String nombre,
        String producto,
        BigDecimal subtotal,
        BigDecimal descuento,
        BigDecimal subtotalConDescuento,
        BigDecimal igv,
        BigDecimal totalConIgv,
        BigDecimal comision,
        BigDecimal totalPagar
) { }
