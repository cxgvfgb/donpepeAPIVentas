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
import pe.donpepe.ventas.dto.ClienteRequest;
import pe.donpepe.ventas.dto.ClienteResponse;
import pe.donpepe.ventas.exception.ValidacionException;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final List<ClienteResponse> clientes = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong(1);

    @PostMapping
    public ClienteResponse crear(@RequestBody ClienteRequest request) {
        validar(request);
        ClienteResponse cliente = new ClienteResponse(
                contador.getAndIncrement(),
                request.nombre(),
                request.dni(),
                request.telefono(),
                request.direccion()
        );
        clientes.add(cliente);
        return cliente;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return List.copyOf(clientes);
    }

    @GetMapping("/{id}")
    public ClienteResponse buscar(@PathVariable Long id) {
        return clientes.stream()
                .filter(c -> c.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ValidacionException("Cliente no encontrado"));
    }

    @PutMapping("/{id}")
    public ClienteResponse actualizar(@PathVariable Long id, @RequestBody ClienteRequest request) {
        validar(request);
        int index = encontrarIndice(id);
        ClienteResponse actualizado = new ClienteResponse(
                id,
                request.nombre(),
                request.dni(),
                request.telefono(),
                request.direccion()
        );
        clientes.set(index, actualizado);
        return actualizado;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        int index = encontrarIndice(id);
        clientes.remove(index);
    }

    private void validar(ClienteRequest request) {
        if (request.nombre() == null || request.nombre().isBlank()) {
            throw new ValidacionException("El nombre es obligatorio");
        }
        if (request.dni() == null || request.dni().isBlank()) {
            throw new ValidacionException("El DNI es obligatorio");
        }
    }

    private int encontrarIndice(Long id) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).id().equals(id)) {
                return i;
            }
        }
        throw new ValidacionException("Cliente no encontrado");
    }
}
