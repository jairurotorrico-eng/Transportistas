package com.tubalcain.Assembler;

import com.tubalcain.domain.Ruta;
import com.tubalcain.dto.RutaDTO;

/**
 *
 * @author Jairo
 */
public class RutaAssembler {

    public RutaDTO toDto(Ruta entity) {
        if (entity == null) {
            return null;
        }

        RutaDTO dto = new RutaDTO();
        dto.setId(entity.getId());
        dto.setOrigen(entity.getOrigen());
        dto.setDestino(entity.getDestino());
        dto.setFechaSalida(entity.getFechaSalida());
        dto.setFechaLlegada(entity.getFechaLlegada());

        // Si la ruta tiene un transportista asignado, sacamos su ID
        if (entity.getTransportista() != null) {
            dto.setTransportistaId(entity.getTransportista().getId());
        }

        return dto;
    }

    public Ruta toEntity(RutaDTO dto) {
        if (dto == null) {
            return null;
        }

        Ruta entity = new Ruta();
        entity.setOrigen(dto.getOrigen());
        entity.setDestino(dto.getDestino());
        entity.setFechaSalida(dto.getFechaSalida());
        entity.setFechaLlegada(dto.getFechaLlegada());
        // El transportista no se asigna aquí, se hace en el Service buscando por ID

        return entity;
    }
}
