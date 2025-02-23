package com.srgi.service.archivo;

import com.srgi.model.Archivo;
import com.srgi.model.Requerimiento;
import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {
    List<Archivo> archivosUpload(List<MultipartFile> file, Integer requerimientoId, Integer comentarioId);
    ResponseEntity<UrlResource> descargarArchivo(Integer archivoId);
}
