package com.example.SpringExercise2.seguridadJwt;

import com.example.SpringExercise2.data.usuarios.UserEntity;
import com.example.SpringExercise2.data.usuarios.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            List<SimpleGrantedAuthority> authorities = userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();
            return new User(userEntity.getEmail(), userEntity.getContraseña(), authorities);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }
}
