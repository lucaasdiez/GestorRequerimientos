package com.srgi.service.archivo;

import com.srgi.model.Archivo;
import com.srgi.model.Requerimiento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {
    String archivosUpload(List<MultipartFile> file);
}
