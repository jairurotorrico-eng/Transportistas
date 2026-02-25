/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tubalcain.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RutaDTO {

    private Long id;

    private String origen;

    private String destino;

    private LocalDate fechaSalida;

    private LocalDate fechaLlegada;


    private Long transportistaId;
}
