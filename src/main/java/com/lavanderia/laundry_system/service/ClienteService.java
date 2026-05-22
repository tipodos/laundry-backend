package com.lavanderia.laundry_system.service;
import com.lavanderia.laundry_system.repository.OrdenRepository;
import com.lavanderia.laundry_system.model.Cliente;
import com.lavanderia.laundry_system.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final OrdenRepository ordenRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Cliente guardar(Cliente cliente) {
        if (cliente.getDni() != null &&
                clienteRepository.existsByDni(cliente.getDni())) {
            throw new RuntimeException("Ya existe un cliente con ese DNI");
        }
        return clienteRepository.save(cliente);

    }

    public Cliente actualizar(Integer id, Cliente clienteNuevo) {
        Cliente clienteExistente = buscarPorId(id);
        clienteExistente.setNombre(clienteNuevo.getNombre());
        clienteExistente.setDni(clienteNuevo.getDni());
        clienteExistente.setCelular(clienteNuevo.getCelular());
        return clienteRepository.save(clienteExistente);
    }

    public void eliminar(Integer id) {
        if (ordenRepository.existsByClienteId(id)) {
            throw new RuntimeException("No se puede eliminar, el cliente tiene órdenes registradas");
        }
        clienteRepository.deleteById(id);
    }
}