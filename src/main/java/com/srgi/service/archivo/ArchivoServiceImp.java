package com.srgi.service.archivo;

import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
import com.srgi.model.FileData;
import com.srgi.repository.ArchivoRepository;
import com.srgi.repository.FileDataRepository;
import com.srgi.utils.ArchivoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//VERIFICAR EL FUNCIONAMIENTO DE GUARDADO DE ARCHIVOS
@RequiredArgsConstructor
@Service
public class ArchivoServiceImp implements ArchivoService{
    private final ArchivoRepository archivoRepository;
    private final FileDataRepository fileDataRepository;
    @Value("${flies.location}")
    private String archivosLocation;

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
    public List<Archivo> guardarArchivo(List<MultipartFile> files) {
        List<Archivo> guardadosArchivos = new ArrayList<>();
        if(files.isEmpty()){
            throw new RuntimeException("Archivo no encontrados");
        }
        for(MultipartFile file : files){
            try {
                Archivo archivo = archivoRepository.save(Archivo.builder()
                        .nombre(file.getOriginalFilename())
                        .tipo(file.getContentType())
                        .archivoData(ArchivoUtils.compressImage(file.getBytes())).build());

                guardadosArchivos.add(archivo);
            }catch (IOException  e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return guardadosArchivos;

    }

    @Override
    public byte[] descargarArchivo(String nombreArchivo) {
        Optional<Archivo> dbarchivo = archivoRepository.findById(Integer.parseInt(nombreArchivo));
        byte[] archivo = ArchivoUtils.decompressImage(dbarchivo.get().getArchivoData());
        return archivo;
    }


    @Override
    public String guardarArchivoToCarpeta(MultipartFile file){
        String filePath = archivosLocation+file.getOriginalFilename();
        try {
            FileData fileData = fileDataRepository.save(FileData.builder()
                    .nombre(file.getOriginalFilename())
                    .tipo(file.getContentType())
                    .filePath(filePath).build());
            file.transferTo(new File(filePath));

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());

        }
        return "Archivo guardado correctamente";
    }

    @Override
    public byte[] descargarArchivoFromCarpeta(String nombreArchivo) {
        try {
            Optional<FileData> filedata = fileDataRepository.findByNombre(nombreArchivo);
            String filepath = filedata.get().getFilePath();
            return Files.readAllBytes(new File(filepath).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
