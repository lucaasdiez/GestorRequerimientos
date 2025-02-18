package com.srgi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UExternoDTO extends UsuarioDTO{

    @Column(nullable = false, unique = true, length = 11)
    @NotBlank(message = "El CUIL es obligatorio.")
    @Pattern(regexp = "\\d{11}", message = "El CUIL debe ser un número de 11 dígitos.")
    private String cuil;

    @Column(nullable = false)
    @NotBlank(message = "La descripción es obligatoria.")
    private String descripcion;

    @Column(nullable = false)
    @NotBlank(message = "La empresa es obligatoria.")
    private String empresa;

    private boolean preferencia;
}
