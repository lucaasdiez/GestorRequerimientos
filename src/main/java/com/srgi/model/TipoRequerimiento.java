package com.srgi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoRequerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String codigo;

    private String descripcion;

    @OneToMany(mappedBy = "tipoRequerimiento")
    @JsonIgnore
    private List<Requerimiento> requerimientos;

    @OneToOne
    @JoinColumn(name = "categoria_req_id")
    private CategoriaRequerimiento categoriaRequerimiento;

}
