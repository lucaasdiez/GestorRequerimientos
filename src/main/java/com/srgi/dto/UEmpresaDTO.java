package com.srgi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UEmpresaDTO extends UsuarioDTO {
    private String cargo;
    private int departamento;
    private int legajo;

}
