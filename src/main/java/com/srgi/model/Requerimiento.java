package com.srgi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;

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
    private int codigo;
    private String descripcion;
    private String estado;
    private Date fechaAlta;
    private LocalTime horaAlta;
    private String prioridad;

    @ManyToOne
    @JoinColumn(name = "tipo_requerimiento_id")
    private TipoRequerimiento tipoRequerimiento;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    @JsonIgnore
    private Usuario emisor;

    @OneToOne(mappedBy = "requerimientoPropietario")
    private Usuario usuarioPropietario;

    @OneToMany(mappedBy = "requerimiento")
    private List<Evento> eventos;

    @OneToMany(mappedBy = "requerimiento")
    private List<Archivo> archivos;

    @OneToMany(mappedBy = "requerimiento")
    private List<Comentario> comentarios;



}
