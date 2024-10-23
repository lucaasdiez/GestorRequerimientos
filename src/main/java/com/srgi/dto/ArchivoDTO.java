package com.srgi.dto;

import com.srgi.model.Archivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoDTO {
    private Integer id;
    private String tipo;
    private String url;

    public Archivo toEntity() {
        Archivo archivo = new Archivo();
        archivo.setId(id);
        archivo.setTipo(tipo);
        archivo.setUrl(url);
        return archivo;
    }
}
