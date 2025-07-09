package com.example.AuthService.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correo;
    private String clave;
    private String username;
    private Rol rol;
    private String nombre;
    private String biografia;
    private String fotoPerfil;
    private Boolean activo;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    @ManyToMany
    @JoinTable(
      name = "usuario_rol", 
      joinColumns = @JoinColumn(name = "usuario_id"), 
      inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles;

}
