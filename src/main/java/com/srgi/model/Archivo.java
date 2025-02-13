package com.srgi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String ruta;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "requerimiento_id")
    @JsonIgnore
    private Requerimiento requerimiento;
}
