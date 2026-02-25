
package com.tubalcain.service;

import com.tubalcain.domain.Transportista;
import com.tubalcain.domain.User;
import com.tubalcain.domain.User.Role;
import com.tubalcain.dto.LoginRequest;
import com.tubalcain.dto.RegisterRequest;
import com.tubalcain.repository.TransportistaRepository;
import com.tubalcain.repository.UserRepository;
import com.tubalcain.security.JwtTokenProvider;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
   @Autowired
    private UserRepository userRepository;
@Autowired
private TransportistaRepository transportistaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    // --- REGISTRO ---
    public Map<String, Object> register(RegisterRequest request) {
        
        // 1. Validamos por EMAIL (No Username)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // 2. Creamos el usuario
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // 3. Asignamos rol (Si viene null, es TRANSPORTISTA por defecto)
        user.setRole(request.getRole() != null ? request.getRole() : Role.TRANSPORTISTA);

        // TODO: Aquí deberíamos guardar también en la tabla 'Transportista' usando request.getNombre()
        user = userRepository.save(user);

        // 5. Creamos el perfil en la tabla transportistas
        if (user.getRole() == Role.TRANSPORTISTA) {
            Transportista transportista = new Transportista();
            transportista.setUser(user); 
            transportista.setNombre(request.getNombre()); 
            transportista.setTelefono(request.getTelefono()); 
            
            // Guardamos el perfil en la base de datos
            transportistaRepository.save(transportista);
        }
        
        
        
        
        
        
        
        
        
        
        // 4. Generamos token y respuesta
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        response.put("message", "Usuario registrado correctamente");

        return response;
    }

    // --- LOGIN ---
    public Map<String, Object> login(LoginRequest request) {
        // 1. Autenticamos
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Generamos token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        // 3. Obtenemos datos del usuario
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        
        return response;
    }
}

