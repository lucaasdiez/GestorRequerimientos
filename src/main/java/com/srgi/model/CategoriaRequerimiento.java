package com.srgi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaRequerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descripcion;

    private boolean desactivado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_requerimiento_id")
    @JsonBackReference
    private TipoRequerimiento tipoRequerimiento;

}
