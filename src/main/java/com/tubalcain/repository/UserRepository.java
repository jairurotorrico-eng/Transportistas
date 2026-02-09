
package com.tubalcain.repository;

import com.tubalcain.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    
    
        //Select * From users where email = ??
        Optional<User> findByEmail(String email);
        
        //Validamos si existe
         boolean existsByEmail(String email);
    
}
