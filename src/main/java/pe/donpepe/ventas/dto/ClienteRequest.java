package pe.donpepe.ventas.dto;

public record ClienteRequest(
        String nombre,
        String dni,
        String telefono,
        String direccion
) { }
