package com.srgi.service.archivo;

import com.srgi.dto.ArchivoDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
import com.srgi.model.Comentario;
import com.srgi.model.Requerimiento;
import com.srgi.repository.ArchivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArchivoServiceImp implements ArchivoService{
    private final ArchivoRepository archivoRepository;

    @Override
    public Archivo getArchivoById(Integer id) {
        return archivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Archivo no encontrado"));
    }

    @Override
    public void deleteArchivo(Integer id) {
        archivoRepository.findById(id).ifPresentOrElse(archivoRepository :: delete,() ->{
            throw new ResourceNotFoundExeption("Archivo no encontrado con id " + id);
        } );
    }

    @Override
    public List<ArchivoDTO> guardarArchivo(List<MultipartFile> archivos, Comentario comentario, Requerimiento requerimiento) {
        List<ArchivoDTO> guardadosArchivosDTO = new ArrayList<>();
        for(MultipartFile archivo : archivos){
            try {
                Archivo archivoguardar = new Archivo();
                archivoguardar.setTipo(archivo.getContentType());
                archivoguardar.setArchivo(new SerialBlob(archivo.getBytes()));
                archivoguardar.setComentario(comentario);
                archivoguardar.setRequerimiento(requerimiento);

                String armarDescargaUrl ="/archivos/archivo/descargar";
                String descargaUrl =armarDescargaUrl+ archivoguardar.getId();
                archivoguardar.setUrl(descargaUrl);
                Archivo imagenGuardada = archivoRepository.save(archivoguardar);

                imagenGuardada.setUrl(armarDescargaUrl+imagenGuardada.getId());
                archivoRepository.save(imagenGuardada);

                ArchivoDTO archivoDTO = new ArchivoDTO();
                archivoDTO.setUrl(imagenGuardada.getUrl());
                archivoDTO.setId(imagenGuardada.getId());
                archivoDTO.setTipo(imagenGuardada.getTipo());
                guardadosArchivosDTO.add(archivoDTO);

            }catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return guardadosArchivosDTO;

    }

    @Override
    public void updateArchivo(Integer id, MultipartFile archivo) {
        Archivo archivo1 = getArchivoById(id);
        try {
            archivo1.setTipo(archivo.getContentType());
            archivo1.setArchivo(new SerialBlob(archivo.getBytes()));
            archivoRepository.save(archivo1);
        }catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
