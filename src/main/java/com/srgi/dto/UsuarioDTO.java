package com.srgi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String username;
    private String role;
    private boolean activado;
    private long cuil;
    private String descripcion;
    private String empresa;
    private boolean preferencia;


}
