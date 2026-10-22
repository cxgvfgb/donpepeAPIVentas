package pe.donpepe.ventas.dto;

public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock
) { }
