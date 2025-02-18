package com.srgi.controller;

import com.srgi.response.ApiResponse;
import com.srgi.service.archivo.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;


@RequiredArgsConstructor
@RestController
@RequestMapping("/archivos")
public class ArchivoController {
    private final ArchivoService archivoService;

    @GetMapping("archivo/descargar/{id}")
    public ResponseEntity<UrlResource> descargarArchivo(@PathVariable Integer id) {
             return archivoService.descargarArchivo(id);
    }
}
