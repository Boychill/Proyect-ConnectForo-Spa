package com.example.AuthService.service;

import com.example.AuthService.model.entity.Rol;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.repository.UsuariosRepository;
import com.example.AuthService.security.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class ServicioAutenticacion {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuariosRepository usuarioRepository;

    public String login(LoginRequest request) {
        Usuarios usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getClave().equals(request.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return jwtUtil.generarToken(usuario);
    }

    public void registrar(RegistroRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Usuario ya existe");
        }

        Usuarios nuevo = new Usuarios();
        nuevo.setUsername(request.getUsername());
        nuevo.setCorreo(request.getCorreo());
        nuevo.setClave(passwordEncoder.encode(request.getClave()));
        nuevo.setRol(Rol.USUARIO);

        usuarioRepository.save(nuevo);
    }
}
