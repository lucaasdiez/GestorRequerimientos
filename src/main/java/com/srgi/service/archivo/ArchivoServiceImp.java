package com.srgi.service.archivo;

import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
import com.srgi.model.Comentario;
import com.srgi.model.Requerimiento;
import com.srgi.repository.ArchivoRepository;
import com.srgi.utils.ArchivoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class ArchivoServiceImp implements ArchivoService{
    private final ArchivoRepository archivoRepository;
    @Value("${flies.location}")
    private String carpetaPath;


    @Override
    public String archivosUpload(List<MultipartFile> files) {
        try {
            for(MultipartFile file : files){
                String fileName= UUID.randomUUID().toString();
                byte[] bytes = file.getBytes();
                String fileOriginalName = file.getOriginalFilename();
                assert fileOriginalName != null;

                long fileSize = file.getSize();
                long maxFileSize = 5 * 1024 * 1024;
                if(fileSize > maxFileSize){
                    return "Archivo debe ser menor o igual a 5MB";
                }
                if(!fileOriginalName.endsWith(".pdf") && !fileOriginalName.endsWith(".docx") && !fileOriginalName.endsWith(".xlsx")){
                    return "El archivo debe ser PDF, WORD o EXCEL";
                }
                String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
                String newFileName = fileOriginalName + fileExtension;
                Path path = Paths.get(carpetaPath +  newFileName);
                Files.write(path, bytes);
            }
            return "Archivo Instalado Correctamente";
        }catch (Exception e){
            throw new ResourceNotFoundExeption(e.getMessage());
        }
    }
}
