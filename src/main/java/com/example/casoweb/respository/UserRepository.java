package com.example.casoweb.respository;

import com.example.casoweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {

    // El método buscará en la tabla users un registro donde el campo username coincida con el valor proporcionado.
    User findByUsername (String username);
}
