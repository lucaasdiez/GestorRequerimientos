package com.srgi.controller;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Requerimiento;
import com.srgi.response.ApiResponse;
import com.srgi.service.requerimiento.RequerimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requerimientos")
public class RequerimientoController {
    private final RequerimientoService requerimientoService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> listaRequerimientosPropietario(@PathVariable Integer id) {
        try {
            List<Requerimiento> requerimientos = requerimientoService.getRequerimientosById(id);
            List<RequerimientoDTO> requerimientoDTOS =requerimientoService.convertirARequerimientoDTO(requerimientos);
            return ResponseEntity.ok(new ApiResponse("Requerimientos", requerimientoDTOS));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Requerimiento no encontrado", null));
        }

    }
}
