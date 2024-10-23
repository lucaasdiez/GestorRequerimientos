package com.srgi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Archivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tipo;
    @Lob
    private Blob archivo;
    private String url;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "requerimiento_id")
    private Requerimiento requerimiento;
}
