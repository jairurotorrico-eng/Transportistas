
package com.tubalcain.repository;

import com.tubalcain.domain.Vehiculo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VehiculoRepository  extends JpaRepository<Vehiculo,Long>{
    
    //no haya matriculas duplicadas
    boolean existsByMatricula(String matricula);
    Optional<Vehiculo> findByMatricula(String matricula);
    
    //Segementación: buscamos el vehículo asiganado a un transportista específico
    Optional<Vehiculo> findByTransportistaId(Long transportistaId);
    
    
    
    
}
