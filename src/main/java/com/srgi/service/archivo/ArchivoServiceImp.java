package com.srgi.service.archivo;

import com.srgi.exeptions.ArchivoExeption;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
import com.srgi.model.Comentario;
import com.srgi.model.Requerimiento;
import com.srgi.repository.ArchivoRepository;
import com.srgi.repository.ComentarioRepository;
import com.srgi.repository.RequerimientoRepository;
import com.srgi.service.comentario.ComentarioService;
import com.srgi.service.requerimiento.RequerimientoService;
import com.srgi.utils.ArchivoUtils;
import jakarta.annotation.Resource;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ArchivoServiceImp implements ArchivoService{
    private final ArchivoRepository archivoRepository;
    private final RequerimientoRepository requerimientoRepository;
    private final ComentarioRepository comentarioRepository;
    @Value("${flies.location}")
    private String carpetaPath;


    @Override
    public List<Archivo> archivosUpload(List<MultipartFile> files, Integer requerimientoId, Integer comentarioId) {
        Requerimiento req = requerimientoRepository.findById(requerimientoId)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
        Comentario com = (comentarioId != null) ? comentarioRepository.findById(comentarioId).orElse(null) : null;
        List<Archivo> archivos = new ArrayList<>();
        try {
            for(MultipartFile file : files){
                if(files.isEmpty()){continue;}
                String fileName= UUID.randomUUID().toString();
                byte[] bytes = file.getBytes();
                String fileOriginalName = file.getOriginalFilename();
                assert fileOriginalName != null;

                long fileSize = file.getSize();
                if(fileSize==0){continue;}
                long maxFileSize = 5 * 1024 * 1024;
                if(fileSize > maxFileSize){
                    throw new ArchivoExeption("Archivo excedido");
                }
                if(!fileOriginalName.endsWith(".pdf") && !fileOriginalName.endsWith(".docx") && !fileOriginalName.endsWith(".xlsx")){
                    throw new ArchivoExeption("Archivo debes ser WORD, PDF o EXCEL");
                }
                String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
                String newFileName = fileName + fileExtension;
                Path path = Paths.get(carpetaPath +"/"+  newFileName);
                Files.write(path, bytes);

                Archivo archivo = new Archivo();
                archivo.setNombre(fileOriginalName);
                archivo.setRuta(path.toString());
                archivo.setRequerimiento(req);
                if (com != null){
                    archivo.setComentario(com);
                }
                archivoRepository.save(archivo);
                archivos.add(archivo);
            }
            return archivos;
        }catch (ResourceNotFoundExeption e ){
            throw new ResourceNotFoundExeption(e.getMessage());
        }catch (ArchivoExeption exeption){
            throw new ArchivoExeption(exeption.getMessage());
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<UrlResource> descargarArchivo(Integer archivoId) {
        try {
            Archivo archivo = archivoRepository.findById(archivoId)
                    .orElseThrow(() -> new ResourceNotFoundExeption("Archivo no encontrado con id: " + archivoId));

            // Validar que el archivo existe en el sistema de archivos
            Path filePath = Paths.get(archivo.getRuta());
            if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
                throw new FileNotFoundException("El archivo no existe en la ruta especificada: " + archivo.getRuta());
            }

            // Crear el recurso para la descarga
            UrlResource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("El archivo no es accesible para lectura");
            }

            // Verificar que el nombre coincida
            String fileName = filePath.getFileName().toString();
            if (!fileName.equals(new File(archivo.getRuta()).getName())) {
                throw new IllegalArgumentException("El nombre del archivo no coincide con el registrado");
            }

            return ResponseEntity.ok()
                    .contentType(Files.probeContentType(filePath) == null ? MediaType.APPLICATION_OCTET_STREAM : MediaType.parseMediaType(Files.probeContentType(filePath)))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + archivo.getNombre() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Error al descargar el archivo: " + e.getMessage());
        }
    }
}
