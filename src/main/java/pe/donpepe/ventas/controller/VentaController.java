package pe.donpepe.ventas.controller;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.donpepe.ventas.dto.VentaRequest;
import pe.donpepe.ventas.dto.VentaResponse;
import pe.donpepe.ventas.exception.ValidacionException;
import pe.donpepe.ventas.service.CalculadoraVenta;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final CalculadoraVenta calculadora;

    public VentaController(CalculadoraVenta calculadora) {
        this.calculadora = calculadora;
    }

    @PostMapping("/calcular")
    public VentaResponse calcular(@RequestBody VentaRequest venta) {
        validar(venta);
        return calculadora.calcular(venta);
    }

    private void validar(VentaRequest venta) {
        if (venta == null) {
            throw new ValidacionException("Los datos de la venta son obligatorios");
        }
        String nombre = venta.nombre() != null ? venta.nombre().trim() : null;
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre del cliente es obligatorio");
        }
        if (venta.producto() == null || venta.producto().isBlank()) {
            throw new ValidacionException("El nombre del producto es obligatorio");
        }
        if (venta.cantidad() == null) {
            throw new ValidacionException("La cantidad es obligatoria");
        }
        if (venta.cantidad() <= 0) {
            throw new ValidacionException("La cantidad debe ser mayor a 0");
        }
        if (venta.precioUnitario() == null) {
            throw new ValidacionException("El precio unitario es obligatorio");
        }
        if (venta.precioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("El precio unitario debe ser mayor a 0");
        }
        if (venta.metodoPago() == null || venta.metodoPago().isBlank()) {
            throw new ValidacionException("El metodo de pago es obligatorio");
        }
        if (!"TARJETA".equals(venta.metodoPago())
                && !"EFECTIVO".equals(venta.metodoPago())) {
            throw new ValidacionException("El metodo de pago solo puede ser TARJETA o EFECTIVO");
        }
    }
}
