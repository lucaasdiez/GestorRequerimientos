package com.srgi.dto;

import com.srgi.model.CategoriaRequerimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoRequerimientoDTO {
    private Integer idTipoRequerimiento;
    private String codigo;
    private String descripcion;
    private CategoriaRequerimientoDTO categoriaRequerimientos;
}
