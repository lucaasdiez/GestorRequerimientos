package com.srgi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequerimientoDTO {
    private Integer id;
    @Column(nullable = false)
    @NotBlank(message = "La descripcion es obligatoria.")
    private String descripcion;
    @Column(nullable = false)
    @NotBlank(message = "El codigo del tipo de requerimiento es obligatorio.")
    private String codigoTipoRequerimiento;

    private boolean desactivado;
}
