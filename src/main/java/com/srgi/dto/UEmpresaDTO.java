package com.srgi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UEmpresaDTO extends UsuarioDTO {
    private String cargo;
    private int departamento;
    private int legajo;

}
