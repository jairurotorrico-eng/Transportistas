package com.tubalcain.controller;

import com.tubalcain.service.TransportistaService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transportistas")
@CrossOrigin(origins = "*")
public class TransportistaController {

    @Autowired
    private TransportistaService transportistaService;

    // Consultar perfil trasnportista o admin
    @GetMapping("/mi-perfil")
    @PreAuthorize("hasAnyRole('TRANSPORTISTA', 'ADMIN')")
    public ResponseEntity<?> obtenerMiPerfil(Authentication authentication) {
        try {
            String emailUsuario = authentication.getName();
            return ResponseEntity.ok(transportistaService.obtenerMiPerfil(emailUsuario));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Listar todo solo el admin
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obtenerTodos() {
        return ResponseEntity.ok(transportistaService.obtenerTodos());
    }

    // Eliminar trasnportista solo admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarTransportista(@PathVariable Long id) {
        try {
            transportistaService.eliminarTransportista(id);
            return ResponseEntity.ok(Map.of("mensaje", "Transportista eliminado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Asiganr vehíuclo solo transportista
    @PutMapping("/{transportistaId}/asignar-vehiculo/{vehiculoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> asignarVehiculo(@PathVariable Long transportistaId, @PathVariable Long vehiculoId) {
        try {
            
            return ResponseEntity.ok(transportistaService.asignarVehiculo(transportistaId, vehiculoId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Deasiganr vehiculo solo admin
    @PutMapping("/{transportistaId}/desasignar-vehiculo")
    @PreAuthorize("hasRole('ADMIN')")
    
    public ResponseEntity<?> desasignarVehiculo(@PathVariable Long transportistaId) {
        try {
            return ResponseEntity.ok(transportistaService.desasignarVehiculo(transportistaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Asignar ruta solo admin
    @PutMapping("/{transportistaId}/asignar-ruta/{rutaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> asignarRuta(@PathVariable Long transportistaId, @PathVariable Long rutaId) {
        try {
            return ResponseEntity.ok(transportistaService.asignarRuta(transportistaId, rutaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
        
        
    }

    // Desasignar ruta solo admin
    @PutMapping("/desasignar-ruta/{rutaId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> desasignarRuta(@PathVariable Long rutaId) {
        try {
            return ResponseEntity.ok(transportistaService.desasignarRuta(rutaId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

}
