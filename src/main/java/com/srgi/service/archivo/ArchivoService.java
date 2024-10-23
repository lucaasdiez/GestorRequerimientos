package com.srgi.service.archivo;

import com.srgi.dto.ArchivoDTO;
import com.srgi.model.Archivo;
import com.srgi.model.Comentario;
import com.srgi.model.Requerimiento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {
    Archivo getArchivoById(Integer id);
    void deleteArchivo(Integer id);
    List<ArchivoDTO> guardarArchivo(List<MultipartFile> archivos, Comentario comentario, Requerimiento requerimiento);
    void updateArchivo(Integer id, MultipartFile archivo);
}
