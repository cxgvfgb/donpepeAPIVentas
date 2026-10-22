package pe.donpepe.ventas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.donpepe.ventas.dto.ProductoRequest;
import pe.donpepe.ventas.dto.ProductoResponse;
import pe.donpepe.ventas.exception.ValidacionException;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final List<ProductoResponse> productos = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    @PostMapping
    public ProductoResponse crear(@RequestBody ProductoRequest request) {
        validar(request);
        ProductoResponse producto = new ProductoResponse(
                contador.getAndIncrement(),
                request.nombre(),
                request.descripcion(),
                request.precio(),
                request.stock()
        );
        productos.add(producto);
        return producto;
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return List.copyOf(productos);
    }

    @GetMapping("/{id}")
    public ProductoResponse buscar(@PathVariable Long id) {
        return productos.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ValidacionException("Producto no encontrado"));
    }

    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Long id, @RequestBody ProductoRequest request) {
        validar(request);
        int index = encontrarIndice(id);
        ProductoResponse actualizado = new ProductoResponse(
                id,
                request.nombre(),
                request.descripcion(),
                request.precio(),
                request.stock()
        );
        productos.set(index, actualizado);
        return actualizado;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        int index = encontrarIndice(id);
        productos.remove(index);
    }

    private void validar(ProductoRequest request) {
        if (request.nombre() == null || request.nombre().isBlank()) {
            throw new ValidacionException("El nombre es obligatorio");
        }
        if (request.precio() == null || request.precio() <= 0) {
            throw new ValidacionException("El precio debe ser mayor a 0");
        }
        if (request.stock() == null || request.stock() < 0) {
            throw new ValidacionException("El stock no puede ser negativo");
        }
    }

    private int encontrarIndice(Long id) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).id().equals(id)) {
                return i;
            }
        }
        throw new ValidacionException("Producto no encontrado");
    }
}
