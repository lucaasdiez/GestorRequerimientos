package com.srgi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.srgi.enums.EstadoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalTime;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Requerimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String asunto;
    private String codigo;
    private String descripcion;
    private EstadoEnum estado;
    private Date fechaAlta;
    private LocalTime horaAlta;
    private String prioridad;

    @ManyToMany
    @JoinTable(
            name = "requerimiento_relacionado",
            joinColumns = @JoinColumn(name = "requerimiento_id"),
            inverseJoinColumns = @JoinColumn(name = "requerimiento_relacionado_id")
    )
    private List<Requerimiento> requerimientosRelacionados;

    @ManyToOne
    @JoinColumn(name = "tipo_requerimiento_id")
    private TipoRequerimiento tipoRequerimiento;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    @JsonIgnore
    private Usuario emisor;

    @OneToOne
    @JoinColumn(name = "propietario_id")
    private Usuario usuarioPropietario;

    @OneToMany(mappedBy = "requerimiento")
    private List<Evento> eventos;

    @OneToMany(mappedBy = "requerimiento")
    private List<Archivo> archivos;

    @OneToMany(mappedBy = "requerimiento")
    private List<Comentario> comentarios;


}
