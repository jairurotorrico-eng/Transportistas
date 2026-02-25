

package com.tubalcain.Assembler;

import com.tubalcain.domain.Vehiculo;
import com.tubalcain.dto.VehiculoDTO;

public class VehiculoAssembler {
    public VehiculoDTO toDto(Vehiculo entity) {
        if (entity == null) return null;
        
        VehiculoDTO dto = new VehiculoDTO();
        dto.setId(entity.getId());
        dto.setMarca(entity.getMarca());
        dto.setModelo(entity.getModelo());
        dto.setMatricula(entity.getMatricula());
        return dto;
    }

    // De DTO a Entidad (Para guardar en la Base de Datos)
    public Vehiculo toEntity(VehiculoDTO dto) {
        if (dto == null) return null;
        
        Vehiculo entity = new Vehiculo();
        // El ID no se suele setear al crear uno nuevo, lo genera la BD
        entity.setMarca(dto.getMarca());
        entity.setModelo(dto.getModelo());
        entity.setMatricula(dto.getMatricula());
        return entity;
    }
}
