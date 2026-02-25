package com.tubalcain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "transportistas")
@Data
public class Transportista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String telefono;

    //RELACIÓN CON USER

    @OneToOne(cascade = CascadeType.ALL) //Se borra un transportia también su login
@JsonIgnore //Corrgir bucle infinito
    @JoinColumn(name = "user_id")
    private User user;

    //RELACION CON VEHICULO TRansporista dueño de la relacion
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehiculo_id ")
    private Vehiculo vehiculo;

    //RELACIO CON RUTAS
    @JsonIgnoreProperties
    @OneToMany(mappedBy = "transportista", cascade = CascadeType.ALL)

    private List<Ruta> rutas = new ArrayList<>();

    public Transportista() {
    }

    // Constructor para registrar un nuevo usuario
    public Transportista(String nombre, String telefono, User user) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.user = user;
    }

}
