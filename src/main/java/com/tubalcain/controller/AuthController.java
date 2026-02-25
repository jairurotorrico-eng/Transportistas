
package com.tubalcain.controller;

import com.tubalcain.dto.LoginRequest;
import com.tubalcain.dto.RegisterRequest;
import com.tubalcain.service.AuthService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Pasamos el OBJETO COMPLETO (Request) en vez de variable a variable.
            // Es mucho más limpio y profesional.
            Map<String, Object> response = authService.register(request);
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            // Si el email ya existe, devolvemos error 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Intentamos hacer login
            Map<String, Object> response = authService.login(request);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            // Si falla la contraseña o el usuario no existe: 401 U
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas (Email o Password erróneo)"));
        }
    }
}
