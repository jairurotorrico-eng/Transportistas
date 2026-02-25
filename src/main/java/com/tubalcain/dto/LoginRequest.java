
package com.tubalcain.dto;

//Lo que enviara el usuario

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
   

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un formato de email válido") //+ seguidad
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;


    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y Setters actualizados a 'email'
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
}
