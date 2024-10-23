package com.srgi.dto;

import com.srgi.model.*;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequerimientoDTO {
    private Integer id;
    private String asunto;
    private int codigo;
    private String descripcion;
    private String estado;
    private Date fechaAlta;
    private LocalTime horaAlta;
    private String prioridad;
    private TipoRequerimientoDTO tipoRequerimiento;
    private UsuarioDTO emisor;
    private UsuarioDTO usuarioPropietario;
    private List<EventoDTO> eventos;
    private List<ArchivoDTO> archivos;
    private List<ComentarioDTO> comentarios;
}
