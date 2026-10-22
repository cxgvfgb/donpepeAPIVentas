package pe.donpepe.ventas.dto;

public record ClienteResponse(
        Long id,
        String nombre,
        String dni,
        String telefono,
        String direccion
) { }
