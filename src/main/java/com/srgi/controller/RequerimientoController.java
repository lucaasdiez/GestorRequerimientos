package com.srgi.controller;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.enums.EstadoEnum;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
import com.srgi.model.Requerimiento;
import com.srgi.response.ApiResponse;
import com.srgi.service.archivo.ArchivoService;
import com.srgi.service.requerimiento.RequerimientoService;
import io.swagger.annotations.Api;
import jakarta.validation.constraints.Null;
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

    @GetMapping("/verTodos")
    public ResponseEntity<ApiResponse> verTodos(){
        return ResponseEntity.ok(new ApiResponse("Requerimientos", requerimientoService.verTodos()));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<ApiResponse> listaRequerimientosPropietario(@PathVariable Integer id) {
        List<Requerimiento> requerimientos = requerimientoService.getRequerimientosByPropietarioId(id);
        List<RequerimientoDTO> requerimientoDTOS =requerimientoService.convertirARequerimientosDTO(requerimientos);
        return ResponseEntity.ok(new ApiResponse("Requerimientos", requerimientoDTOS));

    }

    @GetMapping("/requerimiento/{id}")
    public ResponseEntity<ApiResponse> getRequerimiento(@PathVariable Integer id) {
        Requerimiento requerimiento = requerimientoService.getRequerimientoById(id);
        RequerimientoDTO respDTO = requerimientoService.convertirARequerimientoDTO(requerimiento);
        return ResponseEntity.ok(new ApiResponse("Requerimiento", respDTO ));
    }

    @GetMapping("/{username}/filtrar")
    public ResponseEntity<ApiResponse> flitrarRequerimiento(
            @PathVariable String username,
            @RequestParam(required = false) String tipoRequerimiento,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) EstadoEnum estado
    ){
        List<Requerimiento> requerimientos = requerimientoService.getRequerimientoByFiltros(tipoRequerimiento,categoria,estado, username);
        List<RequerimientoDTO> requerimientoDTOS = requerimientoService.convertirARequerimientosDTO(requerimientos);
        return ResponseEntity.ok(new ApiResponse("Requerimientos", requerimientoDTOS));
    }

    @PostMapping("/agregar")
        public ResponseEntity<ApiResponse> agregarRequerimiento(@RequestPart("requerimientoDTO") RequerimientoDTO requerimientoDTO, @RequestPart(value = "archivos", required = false) List<MultipartFile> files) {
            Requerimiento requerimiento = requerimientoService.registrarRequerimiento(requerimientoDTO, files);
            RequerimientoDTO requerimientoFinal = requerimientoService.convertirARequerimientoDTO(requerimiento);
            return ResponseEntity.ok(new ApiResponse("Requerimiento", requerimientoFinal));
    }

    @PutMapping("/{codigo}/cerrar")
    public ResponseEntity<ApiResponse> cerrarRequerimiento(@PathVariable String codigo){
        requerimientoService.cerrarRequerimiento(codigo);
        return ResponseEntity.ok(new ApiResponse("Requerimiento cerrado con exito", null));
    }



}
