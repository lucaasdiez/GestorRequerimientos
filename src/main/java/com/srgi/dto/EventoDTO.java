package com.srgi.dto;

import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {

    private Integer id;
    private String accion;
    private LocalDate fecha;
    private LocalTime hora;
    private UsuarioDTO usuario;
    private RequerimientoDTO requerimiento;

}
