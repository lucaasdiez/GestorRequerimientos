package com.srgi.dto;

import com.srgi.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
    private Integer id;
    private String asunto;
    private String descripcion;
    private Date fecha;
    private LocalTime hora;

    private Usuario usuario;
    private RequerimientoDTO requerimiento;
    private List<MultipartFile> archivos;
}
