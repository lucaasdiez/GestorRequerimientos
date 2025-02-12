package com.srgi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoDTO {
    private Integer id;
    private String nombre;
    private String rutaDescarga;
}
