package com.srgi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UExternoDTO extends UsuarioDTO{
    private int cuil;
    private String descripcion;
    private String empresa;
}
