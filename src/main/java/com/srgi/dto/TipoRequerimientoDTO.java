package com.srgi.dto;

import com.srgi.model.CategoriaRequerimiento;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoRequerimientoDTO {

    @Column(unique = true)
    @NotBlank(message = "El codigo es obligatoria.")
    private String codigo;

    private boolean desactivado; // desactivado=false,  para eliminar

    @Column(nullable = false)
    @NotBlank(message = "La descripcion es obligatoria.")
    private String descripcion;

    private List<CategoriaRequerimientoDTO> categoriaRequerimientos;
}
