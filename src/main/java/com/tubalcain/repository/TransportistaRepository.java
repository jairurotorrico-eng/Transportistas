
package com.tubalcain.repository;

import com.tubalcain.domain.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransportistaRepository extends JpaRepository<Transportista, Long> {
    
    //Cuando se loguea un usuario, tenemos el userId(del token ). Ncesesitamos saber que transportista es
    // SELECT * FROM transportistas WHERE user_id = ?
    <Optional>Transportista findByUserId(Long userId);
    
}
