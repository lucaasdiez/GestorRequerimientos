package com.srgi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoRequerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String codigo;

    private boolean desactivado; // desactivado=false,  para eliminar

    @Column(nullable = false)
    @NotBlank(message = "La descripcion es obligatoria.")
    private String descripcion;

    @OneToMany(mappedBy = "tipoRequerimiento")
    @JsonIgnore
    private List<Requerimiento> requerimientos;

    @OneToMany(mappedBy = "tipoRequerimiento")
    @JsonManagedReference
    private List<CategoriaRequerimiento> categoriaRequerimiento;

}
