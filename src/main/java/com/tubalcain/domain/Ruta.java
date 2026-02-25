package com.tubalcain.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "rutas")
@Data
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private LocalDate fechaLlegada;

    //Un transportista hacer varias rutas, una rota es solo hecha por un transportista?
    @ManyToOne
    @JoinColumn(name = "transportista_id")
       @JsonIgnoreProperties
    private Transportista transportista;

}
