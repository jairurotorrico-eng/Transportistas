
package com.tubalcain.repository;

import com.tubalcain.domain.Ruta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RutaRepository extends JpaRepository<Ruta, Long> {
    
    //Segmentación: devolvería la lista de rutas de un solo transportista
    //Select * from rutas where trasnportistas_id = ?
    
    List<Ruta> findByTransportistaId(Long trasportistaId);
    
    
}
