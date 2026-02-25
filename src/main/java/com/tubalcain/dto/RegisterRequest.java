
package com.tubalcain.dto;

import com.tubalcain.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


public class RegisterRequest {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Tiene que ser un formato de email vlalido")
    private String email;


    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

  
    @NotBlank(message = "nnombre es obligatorio")
    private String nombre;

    private String telefono; 

    private User.Role role;



    public RegisterRequest() {}

    public RegisterRequest(String email, String password, String nombre, String telefono) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.telefono = telefono;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public User.Role getRole() {
  
        return role != null ? role : User.Role.TRANSPORTISTA;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
