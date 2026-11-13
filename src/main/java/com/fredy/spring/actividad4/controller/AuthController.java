package com.fredy.spring.actividad4.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fredy.spring.actividad4.dto.AuthResponse;
import com.fredy.spring.actividad4.dto.LoginRequest;
import com.fredy.spring.actividad4.dto.RegisterRequest;
import com.fredy.spring.actividad4.entity.Usuario;
import com.fredy.spring.actividad4.repository.UsuarioRepository;
import com.fredy.spring.actividad4.security.JwtService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                          JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        Usuario user = new Usuario();
        user.setNombre(request.nombre());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        usuarioRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(
            new AuthResponse(jwtToken, "Bearer", user.getEmail(), user.getNombre()),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        Usuario user = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(
            new AuthResponse(jwtToken, "Bearer", user.getEmail(), user.getNombre())
        );
    }
}