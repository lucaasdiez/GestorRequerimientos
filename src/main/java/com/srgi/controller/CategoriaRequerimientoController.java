package com.srgi.controller;

import com.srgi.dto.CategoriaRequerimientoDTO;
import com.srgi.model.CategoriaRequerimiento;
import com.srgi.response.ApiResponse;
import com.srgi.service.categoriaRequerimiento.CategRequerimientoService;
import com.srgi.service.categoriaRequerimiento.CategoriaRequerimientoServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categRequerimientos")
public class CategoriaRequerimientoController {
    private final CategRequerimientoService categReqService;

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ApiResponse> getCategoriaRequerimiento(@PathVariable Integer id) {
        CategoriaRequerimientoDTO dto = categReqService.getCategoriaRequerimiento(id);
        return ResponseEntity.ok(new ApiResponse("Categoria de requerimiento", dto));
    }

    @GetMapping("/{codigo}/todas")
    public ResponseEntity<ApiResponse> getCategoriaRequerimientoByCodigo(@PathVariable String codigo) {
        List<CategoriaRequerimiento> categoriaRequerimiento = categReqService.getCategoriaByCodigoRequerimiento(codigo);
        List<CategoriaRequerimientoDTO> categoriaRequerimientoDTOS = categReqService.convertirADTOs(categoriaRequerimiento);
        return ResponseEntity.ok(new ApiResponse("Categoria de requerimiento", categoriaRequerimientoDTOS));
    }


    @GetMapping("/todas")
    public ResponseEntity<ApiResponse> verTodosCategoriaRequerimiento(){
        List<CategoriaRequerimientoDTO> categoriaRequerimientoDTOS =  categReqService.getAll();
        return ResponseEntity.ok(new ApiResponse("Categorias de requerimientos ", categoriaRequerimientoDTOS));
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> registrarCategoriaRequerimiento(@RequestBody CategoriaRequerimientoDTO categoriaRequerimiento){
        categReqService.registrarCategoriaRequerimiento(categoriaRequerimiento);
        return ResponseEntity.ok(new ApiResponse("Categoria de requerimiento creada", null));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCaetgoriaRequerimiento(@PathVariable Integer id, @RequestBody CategoriaRequerimientoDTO categoriaRequerimiento){
        categReqService.updateCategoriaRequerimiento(categoriaRequerimiento, id);
        return ResponseEntity.ok(new ApiResponse("Categoria de requerimiento modificada", null));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse> desactivarCategoriaRequerimiento(@PathVariable Integer id){
        categReqService.deleteCategoriaRequerimiento(id);
        return ResponseEntity.ok(new ApiResponse("Categoria de requerimiento desactivada", null));
    }


}
