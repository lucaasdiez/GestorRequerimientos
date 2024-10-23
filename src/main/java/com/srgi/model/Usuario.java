package com.srgi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String usuario;

    @OneToMany(mappedBy = "emisor")
    @JsonIgnore
    private List<Requerimiento> requerimientoEmisor;

    @OneToOne
    @JoinColumn(name = "req_propietario_id")
    private Requerimiento requerimientoPropietario;

    @OneToMany(mappedBy = "usuario")
    private List<Evento> eventos;


}
