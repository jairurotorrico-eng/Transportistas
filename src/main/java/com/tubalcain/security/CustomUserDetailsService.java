
package com.tubalcain.security;

import com.tubalcain.domain.User;
import com.tubalcain.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    //Buscamos por email
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontado"+ email));
     //Convertimos Rol (enum) a Auhtority de Spring
     //Spring espera "ROLE_ADMIN" O "ROLE_TRANSPORTISTA"
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
    
    //Devolvemos el objeto UserDetails que Spring entiende
    
    return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail()) //Email como username
            .password(user.getPassword())
            .authorities(Collections.singletonList(authority)) //Lista un solo rol
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }

  
    
}
