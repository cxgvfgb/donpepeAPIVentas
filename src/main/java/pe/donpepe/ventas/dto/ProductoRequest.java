package pe.donpepe.ventas.dto;

public record ProductoRequest(
        String nombre,
        String descripcion,
        Double precio,
        Integer stock
) { }
