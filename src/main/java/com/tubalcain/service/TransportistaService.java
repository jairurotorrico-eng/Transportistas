
package com.tubalcain.service;

import com.tubalcain.domain.Ruta;
import com.tubalcain.domain.Transportista;
import com.tubalcain.domain.Vehiculo;
import com.tubalcain.repository.RutaRepository;
import com.tubalcain.repository.TransportistaRepository;
import com.tubalcain.repository.VehiculoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportistaService {
    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private RutaRepository rutaRepository;

    // Consultar perfil
    public Transportista obtenerMiPerfil(String emailUsuario) {
        return transportistaRepository.findByUserEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    // Listamos todoss
    public List<Transportista> obtenerTodos() {
        return transportistaRepository.findAll();
    }

    // Borrar un trasnportista
    public void eliminarTransportista(Long id) {
        if (!transportistaRepository.existsById(id)) {
            throw new RuntimeException("Transportista no encontrado");
        }
        transportistaRepository.deleteById(id);
    }

    // Asginar un vehículo a un trasnportista
    public Transportista asignarVehiculo(Long transportistaId, Long vehiculoId) {
        Transportista transportista = transportistaRepository.findById(transportistaId)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));
        
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        // Comprobamos si el vehiculo le pertenece a otra persona
        if (vehiculo.getTransportista() != null && !vehiculo.getTransportista().getId().equals(transportistaId)) {
            throw new RuntimeException("Este vehículo ya está asignado a otro transportista");
        }

        transportista.setVehiculo(vehiculo);
        return transportistaRepository.save(transportista);
    }

    // deasignar un vehiculo
    public Transportista desasignarVehiculo(Long transportistaId) {
        Transportista transportista = transportistaRepository.findById(transportistaId)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));
        
        transportista.setVehiculo(null);
        return transportistaRepository.save(transportista);
    }

    // Asignar un transportista a una ruta
    public Ruta asignarRuta(Long transportistaId, Long rutaId) {
        Transportista transportista = transportistaRepository.findById(transportistaId)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));
        
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        // Como el dueño de la relación es la Ruta (@ManyToOne), seteamos el transportista en la ruta
        ruta.setTransportista(transportista);
        return rutaRepository.save(ruta);
    }

    // Deasiganar una ruta
    public Ruta desasignarRuta(Long rutaId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        
        ruta.setTransportista(null);
        return rutaRepository.save(ruta);
    }
}
