package pe.donpepe.ventas.dto;

import java.math.BigDecimal;

// Detalle de cobro que devuelve la API.
public record VentaResponse(
        BigDecimal subtotal,
        BigDecimal descuento,
        BigDecimal subtotalConDescuento,
        BigDecimal igv,
        BigDecimal totalConIgv,
        BigDecimal comision,
        BigDecimal totalPagar
) { }
