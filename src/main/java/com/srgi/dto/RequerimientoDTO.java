package com.srgi.dto;

import com.srgi.model.*;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequerimientoDTO {
    private Integer id;
    private String asunto;
    private String codigo;
    private String descripcion;
    private String estado;
    private Date fechaAlta;
    private LocalTime horaAlta;
    private String prioridad;
    private TipoRequerimientoDTO tipoRequerimiento;
    private UsuarioDTO emisor;
    private UsuarioDTO usuarioPropietario;
    private List<EventoDTO> eventos;
    private List<MultipartFile> archivos;
    private List<ComentarioDTO> comentarios;
    private List<RequerimientoDTO> requerimientoRelacionado;
}
