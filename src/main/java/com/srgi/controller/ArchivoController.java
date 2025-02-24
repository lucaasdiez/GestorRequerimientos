package com.srgi.controller;

import com.srgi.response.ApiResponse;
import com.srgi.service.archivo.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;


@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/archivos")
public class ArchivoController {
    private final ArchivoService archivoService;

    @GetMapping("archivo/descargar/{id}")
    public ResponseEntity<UrlResource> descargarArchivo(@PathVariable Integer id) {
             return archivoService.descargarArchivo(id);
    }
}
