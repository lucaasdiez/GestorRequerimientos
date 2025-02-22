package com.srgi.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.srgi.enums.EstadoEnum;
import com.srgi.model.*;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequerimientoDTO {
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "El asunto es obligatorio.")
    private String asunto;

    private String codigo;

    @Column(nullable = false)
    @NotBlank(message = "La descripcion es obligatoria.")
    private String descripcion;

    @Column(nullable = false)
    @NotBlank(message = "La categoria es obligatoria.")
    private String categRequerimiento;

    private EstadoEnum estado;
    private LocalDate fechaAlta;
    private LocalTime horaAlta;
    private String prioridad;
    private TipoRequerimientoDTO tipoRequerimiento;
    private UExternoDTO emisor;
    private UExternoDTO propietario;
    private List<ArchivoDTO> archivos;
    private List<ComentarioDTO> comentarios;
    private List<String> codigoRequerimientoRelacionado;
}
