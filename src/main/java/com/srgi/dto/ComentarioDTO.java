package com.srgi.dto;

import com.srgi.model.Usuario;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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

    @Column(nullable = false)
    @NotBlank(message = "El asunto es obligatorio.")
    private String asunto;

    @Column(nullable = false)
    @NotBlank(message = "La descripcion es obligatoria.")
    private String descripcion;

    private Date fecha;
    private LocalTime hora;

    private Usuario usuario;
    private RequerimientoDTO requerimiento;
    private List<MultipartFile> archivos;
}
