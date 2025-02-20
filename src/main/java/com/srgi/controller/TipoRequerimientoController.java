package com.srgi.controller;

import com.srgi.dto.TipoRequerimientoDTO;
import com.srgi.model.TipoRequerimiento;
import com.srgi.response.ApiResponse;
import com.srgi.service.tipoRequerimiento.TipoRequerimientoService;
import com.srgi.service.tipoRequerimiento.TipoRequerimientoServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tiposRequerimientos")
public class TipoRequerimientoController {

    private final TipoRequerimientoService tipoRequerimientoService;

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarTipoRequerimiento(@RequestBody TipoRequerimientoDTO tipoRequerimiento){
        tipoRequerimientoService.registrarTipoRequerimiento(tipoRequerimiento);
        return ResponseEntity.ok(new ApiResponse("Tipo de Requerimiento registrado", null));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> verTodosTipoRequerimiento(){
        return ResponseEntity.ok(new ApiResponse("Tipo de Requerimientos",
                tipoRequerimientoService.getTipoRequerimiento()));
    }

    @GetMapping("/{estadoEliminacion}/todos")
    public ResponseEntity<ApiResponse> getTipoRequerimientoByEstadoEliminacion(@PathVariable boolean estadoEliminacion){
        return ResponseEntity.ok(new ApiResponse("Tipo de Requerimientos",
                tipoRequerimientoService.getTipoRequerimientoByDesactivado(estadoEliminacion)));
    }

    @GetMapping("/{codigo}/tipoRequerimiento")
    public ResponseEntity<ApiResponse> verTipoRequerimiento(@PathVariable String codigo){
        return ResponseEntity.ok(new ApiResponse("Tipo de Requerimiento ",
                tipoRequerimientoService.getTipoRequerimiento(codigo)));
    }

    @PutMapping("/{codigo}/updateTipo")
    public ResponseEntity<ApiResponse> updateTipoRequerimiento(@PathVariable String codigo,@RequestBody TipoRequerimientoDTO tipo){
        tipoRequerimientoService.updateTipoRequerimiento(tipo, codigo);
        return ResponseEntity.ok(new ApiResponse("Tipo de Requerimiento actualizado", null));
    }

    @PatchMapping("/{codigo}/desactivar")
    public ResponseEntity<ApiResponse> desactivarTipoRequerimiento(@PathVariable String codigo){
        tipoRequerimientoService.eliminarTipoRequerimiento(codigo);
        return ResponseEntity.ok(new ApiResponse("Tipo de Requerimiento desactivado", null));
    }

    @PatchMapping("/{codigo}/reactivar")
    public ResponseEntity<ApiResponse> reactivarTipoRequerimiento(@PathVariable String codigo){
        tipoRequerimientoService.reactivarTipoRequerimiento(codigo);
        return ResponseEntity.ok(new ApiResponse("Tipo de Requerimiento reactivado", null));
    }

}
