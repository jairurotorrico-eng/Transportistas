package com.tubalcain.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vehiculos")
@Data
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    @Column(unique = true) 
    private String matricula; //Identificativo
    
    @OneToOne (mappedBy = "vehiculo")//Un Transportista tiene su vehíuculo y un vehículo teiene su transportista
        @JsonIgnoreProperties
    private Transportista transportista;

}
