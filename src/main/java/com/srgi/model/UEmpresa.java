package com.srgi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UEmpresa extends Usuario{

    @Column(nullable = false)
    @NotBlank(message = "El cargo es obligatorio.")
    private String cargo;


    @Column(nullable = false)
    @NotBlank(message = "El departamento es obligatorio.")
    private String departamento;

    @Column(nullable = false, unique = true, length = 11)
    @NotBlank(message = "El legajo es obligatorio.")
    @Pattern(regexp = "\\d{7,8}", message = "El legajo debe ser un número entre 7 y 8 digitos.")
    private int legajo;
}
