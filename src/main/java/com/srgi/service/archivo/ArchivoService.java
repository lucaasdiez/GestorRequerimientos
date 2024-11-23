package com.srgi.service.archivo;

import com.srgi.model.Archivo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchivoService {
    Archivo getArchivoById(Integer id);
    void deleteArchivo(Integer id);
    List<Archivo> guardarArchivo(List<MultipartFile> archivos);
    byte[] descargarArchivo(String nombreArchivo);
    String guardarArchivoToCarpeta(MultipartFile archivo);
    byte[] descargarArchivoFromCarpeta(String nombreArchivo);
}
