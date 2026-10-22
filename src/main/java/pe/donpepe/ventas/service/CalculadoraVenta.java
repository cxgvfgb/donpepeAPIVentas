package pe.donpepe.ventas.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import pe.donpepe.ventas.dto.VentaRequest;
import pe.donpepe.ventas.dto.VentaResponse;

@Service
public class CalculadoraVenta {

    private static final BigDecimal LIMITE_DESCUENTO = new BigDecimal("500.00");
    private static final BigDecimal TASA_DESCUENTO = new BigDecimal("0.10");
    private static final BigDecimal TASA_IGV = new BigDecimal("0.18");
    private static final BigDecimal TASA_COMISION = new BigDecimal("0.03");

    // Responsabilidad unica: calcular importes de la venta.
    public VentaResponse calcular(VentaRequest venta) {
        BigDecimal subtotal = redondear(
                venta.precioUnitario().multiply(BigDecimal.valueOf(venta.cantidad()))
        );

        BigDecimal descuento = subtotal.compareTo(LIMITE_DESCUENTO) > 0
                ? redondear(subtotal.multiply(TASA_DESCUENTO))
                : redondear(BigDecimal.ZERO);

        BigDecimal subtotalConDescuento = redondear(subtotal.subtract(descuento));
        BigDecimal igv = redondear(subtotalConDescuento.multiply(TASA_IGV));
        BigDecimal totalConIgv = redondear(subtotalConDescuento.add(igv));

        BigDecimal comision = "TARJETA".equals(venta.metodoPago())
                ? redondear(totalConIgv.multiply(TASA_COMISION))
                : redondear(BigDecimal.ZERO);

        BigDecimal totalPagar = redondear(totalConIgv.add(comision));

        return new VentaResponse(
                venta.nombre(),
                venta.producto(),
                subtotal,
                descuento,
                subtotalConDescuento,
                igv,
                totalConIgv,
                comision,
                totalPagar
        );
    }

    private BigDecimal redondear(BigDecimal monto) {
        return monto.setScale(2, RoundingMode.HALF_UP);
    }
}
