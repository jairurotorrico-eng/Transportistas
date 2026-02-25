
package com.tubalcain.service;

import com.tubalcain.domain.Ruta;
import com.tubalcain.domain.Transportista;
import com.tubalcain.repository.RutaRepository;
import com.tubalcain.repository.TransportistaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RutaService {
    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private TransportistaRepository transportistaRepository;

    // Crear una nueva ruta
    public Ruta crearRuta(Ruta ruta) {
        // Al crearla, puede que aún no tenga transportista asignado, y está bien.
        return rutaRepository.save(ruta);
    }

    // Modicar datos de la ruta
    public Ruta modificarRuta(Long id, Ruta rutaActualizada) {
        Ruta rutaExistente = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + id));

        // Actualizamos los datos básic
        rutaExistente.setOrigen(rutaActualizada.getOrigen());
        rutaExistente.setDestino(rutaActualizada.getDestino());
        rutaExistente.setFechaSalida(rutaActualizada.getFechaSalida());
        rutaExistente.setFechaLlegada(rutaActualizada.getFechaLlegada());

        return rutaRepository.save(rutaExistente);
    }

    // eliminar ruta
    public void eliminarRuta(Long id) {
        if (!rutaRepository.existsById(id)) {
            throw new RuntimeException("Ruta no encontrada con ID: " + id);
        }
        rutaRepository.deleteById(id);
    }

    // Listamos todas las rutas
    public List<Ruta> obtenerTodas() {
        return rutaRepository.findAll();
    }

    // Cinsultar las rutas asiganadar al transportista
    public List<Ruta> obtenerMisRutas(String emailUsuarioLogueado) {
        // Buscamos quién es el transportista a través del email del token
        Transportista transportista = transportistaRepository.findByUserEmail(emailUsuarioLogueado)
                .orElseThrow(() -> new RuntimeException("Perfil de transportista no encontrado"));

        //  Usamos el método que cramos en RutaRepository para sacar solo sus rutas
        return rutaRepository.findByTransportistaId(transportista.getId());
    }
}
