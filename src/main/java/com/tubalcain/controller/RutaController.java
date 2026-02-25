
package com.tubalcain.controller;

import com.tubalcain.domain.Ruta;
import com.tubalcain.service.RutaService;
import java.util.List;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rutas")
@CrossOrigin(origins = "*")
public class RutaController {
 
    @Autowired
    private RutaService rutaService;

    // --- RF-12: Crear una nueva ruta (solo ADMIN) ---
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearRuta(@RequestBody Ruta ruta) {
        try {
            Ruta nuevaRuta = rutaService.crearRuta(ruta);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRuta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // --- RF-13: Modificar datos de una ruta (solo ADMIN) ---
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modificarRuta(@PathVariable Long id, @RequestBody Ruta ruta) {
        try {
            return ResponseEntity.ok(rutaService.modificarRuta(id, ruta));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // --- RF-14: Eliminar una ruta (solo ADMIN) ---
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarRuta(@PathVariable Long id) {
        try {
            rutaService.eliminarRuta(id);
            return ResponseEntity.ok(Map.of("mensaje", "Ruta eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // --- RF-15: Listar todas las rutas (solo ADMIN) ---
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Ruta>> obtenerTodas() {
        return ResponseEntity.ok(rutaService.obtenerTodas());
    }

    // --- RF-16: Consultar únicamente las rutas asignadas al transportista ---
    @GetMapping("/mis-rutas")
    @PreAuthorize("hasRole('TRANSPORTISTA')")
    public ResponseEntity<?> obtenerMisRutas(Authentication authentication) {
        try {
            // Cogemos el email del token de forma segura
            String emailUsuario = authentication.getName();
            
            List<Ruta> misRutas = rutaService.obtenerMisRutas(emailUsuario);
            return ResponseEntity.ok(misRutas);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
    

