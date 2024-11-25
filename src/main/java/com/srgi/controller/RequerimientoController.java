package com.srgi.controller;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
import com.srgi.model.Requerimiento;
import com.srgi.response.ApiResponse;
import com.srgi.service.archivo.ArchivoService;
import com.srgi.service.requerimiento.RequerimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requerimientos")
public class RequerimientoController {
    private final RequerimientoService requerimientoService;
    private final ArchivoService archivoService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<ApiResponse> listaRequerimientosPropietario(@PathVariable Integer id) {
        try {
            List<Requerimiento> requerimientos = requerimientoService.getRequerimientosByPropietarioId(id);
            List<RequerimientoDTO> requerimientoDTOS =requerimientoService.convertirARequerimientosDTO(requerimientos);
            return ResponseEntity.ok(new ApiResponse("Requerimientos", requerimientoDTOS));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Requerimiento no encontrado", null));
        }

    }

    @GetMapping("/requerimiento/{id}")
    public ResponseEntity<ApiResponse> getRequerimiento(@PathVariable Integer id) {
        try {
            Requerimiento requerimiento = requerimientoService.getRequerimientoById(id);
            RequerimientoDTO respDTO = requerimientoService.convertirARequerimientoDTO(requerimiento);
            return ResponseEntity.ok(new ApiResponse("Requerimiento", respDTO ));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Requerimiento no encontrado", null));
        }
    }

    @GetMapping("/flitrar")
        public ResponseEntity<ApiResponse> flitrarRequerimiento(
                @RequestParam(required = false) String tipo,
                @RequestParam(required = false) String categoria,
                @RequestParam(required = false) String estado
        ) {
        try {
            List<Requerimiento> requerimientos = requerimientoService.getRequerimientoByFiltros(tipo,categoria,estado);
            List<RequerimientoDTO> requerimientoDTOS = requerimientoService.convertirARequerimientosDTO(requerimientos);
            return ResponseEntity.ok(new ApiResponse("Requerimientos", requerimientoDTOS));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Requerimientos no encontrados", null));
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarRequerimiento(@RequestPart("requerimientoDTO") RequerimientoDTO requerimientoDTO,
                                                            @RequestPart("archivos") List<MultipartFile> files) {
        try {
            Requerimiento requerimiento = requerimientoService.registrarRequerimiento(requerimientoDTO, files);
            RequerimientoDTO requerimientoFinal = requerimientoService.convertirARequerimientoDTO(requerimiento);
            return ResponseEntity.ok(new ApiResponse("Requerimiento", requerimientoFinal));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Requerimiento no encontrado", null));
        }
    }


}
