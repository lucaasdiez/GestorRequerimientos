package com.srgi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UExternoDTO extends UsuarioDTO{
    private int cuil;
    private String descripcion;
    private String empresa;
}
