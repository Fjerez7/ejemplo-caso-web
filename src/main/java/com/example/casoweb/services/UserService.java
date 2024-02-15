package com.example.casoweb.services;

import com.example.casoweb.models.User;
import com.example.casoweb.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca un usuario en el repositorio por su username.
        User user = userRepository.findByUsername(username);
        // Si el usuario no se encuentra, lanza una excepción.
        if(user==null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        // Si el usuario se encuentra, construye un objeto UserDetails con los detalles del usuario.
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), new ArrayList<>());
    }
}
