package com.example.AuthService.service;

import com.example.AuthService.model.entity.Rol;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.dto.RegistroResponse;
import com.example.AuthService.config.ExcepcionesPersonalizadas;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.LoginResponse;
import com.example.AuthService.repository.UsuariosRepository;
import com.example.AuthService.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioAutenticacion {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UsuariosRepository usuarioRepository;
    
    public LoginResponse autenticar(LoginRequest request) {
        try {
            validarCredenciales(request);
            return realizarAutenticacion(request);
        } catch (Exception e) {
            log.error("Error durante la autenticación: {}", e.getMessage());
            throw new ExcepcionesPersonalizadas("Error en la autenticación");
        }
    }
    
    private void validarCredenciales(LoginRequest request) {
        if (request.getCorreo() == null || request.getCorreo().trim().isEmpty()) {
            throw new ExcepcionesPersonalizadas("El correo es obligatorio");
        }
        
        if (request.getClave() == null || request.getClave().trim().isEmpty()) {
            throw new ExcepcionesPersonalizadas("La contraseña es obligatoria");
        }
        
        if (!request.getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ExcepcionesPersonalizadas("Formato de correo inválido");
        }
    }
    
    private LoginResponse realizarAutenticacion(LoginRequest request) {
        try {
            Usuarios usuario = buscarUsuario(request.getCorreo());
            validarUsuario(usuario);
            validarContraseña(request.getClave(), usuario);
            
            String token = jwtUtil.generarToken(usuario);
            return new LoginResponse(token);
            
        } catch (ExcepcionesPersonalizadas e) {
            log.error("Error de autenticación: {}", e.getMessage());
            throw e;
        }
    }
    
    private Usuarios buscarUsuario(String correo) {
        return usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new ExcepcionesPersonalizadas("Usuario no encontrado"));
    }
    
    private void validarUsuario(Usuarios usuario) {
        if (usuario.getClave() == null) {
            throw new ExcepcionesPersonalizadas("Usuario sin contraseña registrada");
        }
    }
    
    private void validarContraseña(String clave, Usuarios usuario) {
        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new ExcepcionesPersonalizadas("Contraseña incorrecta");
        }
    }
    
    public RegistroResponse registrarUsuario(RegistroRequest request) {
        try {
            validarRegistroRequest(request);
            return realizarRegistro(request);
        } catch (Exception e) {
            log.error("Error durante el registro: {}", e.getMessage());
            throw new ExcepcionesPersonalizadas("Error en el registro de usuario");
        }
    }
    
    private void validarRegistroRequest(RegistroRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new ExcepcionesPersonalizadas("Usuario ya existe");
        }
        
        if (request.getCorreo() == null || !request.getCorreo().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ExcepcionesPersonalizadas("Formato de correo inválido");
        }
        
        if (request.getClave() == null || request.getClave().length() < 6) {
            throw new ExcepcionesPersonalizadas("La contraseña debe tener al menos 6 caracteres");
        }
    }
    
    private RegistroResponse realizarRegistro(RegistroRequest request) {
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setClave(passwordEncoder.encode(request.getClave()));
        nuevoUsuario.setRol(Rol.USUARIO);
        nuevoUsuario.setActivo(true);
        
        Usuarios usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        String token = jwtUtil.generarToken(usuarioGuardado);
        
        return new RegistroResponse(token);
    }
}
