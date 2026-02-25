/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tubalcain.controller;

import com.tubalcain.domain.Vehiculo;
import com.tubalcain.service.VehiculoService;
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
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*") 
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    // Crear un vehíuclo solo el admin
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo nuevoVehiculo = vehiculoService.crearVehiculo(vehiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVehiculo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // MODIFICAR los datos del vehículo solo ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> modificarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        try {
            Vehiculo vehiculoActualizado = vehiculoService.modificarVehiculo(id, vehiculo);
            return ResponseEntity.ok(vehiculoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar vehíuclo el admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarVehiculo(@PathVariable Long id) {
        try {
            vehiculoService.eliminarVehiculo(id);
            return ResponseEntity.ok(Map.of("mensaje", "Vehículo eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // Listar todos los vehículos admin
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Vehiculo>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    // Consultar solo el vehíuclo asiganado al transportisa
    @GetMapping("/mi-vehiculo")
    @PreAuthorize("hasRole('TRANSPORTISTA')") //Solo los transportista pueden entrar a este endpoint
    public ResponseEntity<?> obtenerMiVehiculo(Authentication authentication) {
        try {
            // Gracias al filtro JWT, Spring sabe quién es el usuario logueado.
            // authentication.getName() nos devuelve el email que guardamos en el token.
            String emailUsuario = authentication.getName(); 
            
            Vehiculo miVehiculo = vehiculoService.obtenerMiVehiculo(emailUsuario);
            return ResponseEntity.ok(miVehiculo);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
    
    
    
}
