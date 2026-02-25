
package com.tubalcain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable =false)
    private String email; //Email como usuaario
    
    @Column(nullable = false)
     @JsonIgnore //me salia la contra encriptada, pongo esto para probar 
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
  
    //Los roler que tendremos   
    public enum Role{
        ADMIN,
        TRANSPORTISTA
    }
    
    
}
