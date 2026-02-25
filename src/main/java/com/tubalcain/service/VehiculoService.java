
package com.tubalcain.service;

import com.tubalcain.Assembler.VehiculoAssembler;
import com.tubalcain.domain.Transportista;
import com.tubalcain.domain.Vehiculo;
import com.tubalcain.dto.VehiculoDTO;
import com.tubalcain.repository.TransportistaRepository;
import com.tubalcain.repository.VehiculoRepository;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {
  @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TransportistaRepository transportistaRepository;

    // Crear un nuevo vehículo
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        if (vehiculoRepository.existsByMatricula(vehiculo.getMatricula())) {
            throw new RuntimeException("Ya existe un vehículo con la matrícula: " + vehiculo.getMatricula());
        }
        return vehiculoRepository.save(vehiculo);
    }

    // modificar vehículo
    public Vehiculo modificarVehiculo(Long id, Vehiculo vehiculoActualizado) {
        Vehiculo vehiculoExistente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        if (!vehiculoExistente.getMatricula().equals(vehiculoActualizado.getMatricula()) && 
            vehiculoRepository.existsByMatricula(vehiculoActualizado.getMatricula())) {
            throw new RuntimeException("La nueva matrícula ya está en uso por otro vehículo");
        }

        vehiculoExistente.setMarca(vehiculoActualizado.getMarca());
        vehiculoExistente.setModelo(vehiculoActualizado.getModelo());
        vehiculoExistente.setMatricula(vehiculoActualizado.getMatricula());

        return vehiculoRepository.save(vehiculoExistente);
    }

    // Delete vehiuclo
    public void eliminarVehiculo(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehículo no encontrado con ID: " + id);
        }
        vehiculoRepository.deleteById(id);
    }

    // listar todos los vehiculos
    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    // Consultamos el vehiculo asignado al transportista
    public Vehiculo obtenerMiVehiculo(String emailUsuarioLogueado) {
        Transportista transportista = transportistaRepository.findByUserEmail(emailUsuarioLogueado)
                .orElseThrow(() -> new RuntimeException("Perfil de transportista no encontrado"));

        if (transportista.getVehiculo() == null) {
            throw new RuntimeException("Aún no tienes ningún vehículo asignado a tu perfil.");
        }

        return transportista.getVehiculo();
    }
}
