package com.example.casoweb.controllers;

import com.example.casoweb.models.User;
import com.example.casoweb.respository.UserRepository;
import com.example.casoweb.wire.LoginDto;
import com.example.casoweb.wire.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Intenta autenticar al usuario con las credenciales proporcionadas
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            // Autentica al usuario utilizando el AuthenticationManager y las credenciales proporcionadas.
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            // Si la autenticación es exitosa, establece el contexto de seguridad con la autenticación obtenida
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("Inicio de sesion exitoso!", HttpStatus.OK);
        }catch (BadCredentialsException err){
            // Si las credenciales son incorrectas, devuelve una respuesta
            return new ResponseEntity<>("Username o contraseña incorrectos.", HttpStatus.UNAUTHORIZED);
        }catch (Exception err){
            // Si ocurre cualquier otro error, devuelve una respuesta
            return new ResponseEntity<>("Ocurrió un error durante el inicio de sesión.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Registra un nuevo usuario con los datos proporcionados
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signUpDto) {
        // creating user object
        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Usuario registrado!", HttpStatus.OK);
    }
}
