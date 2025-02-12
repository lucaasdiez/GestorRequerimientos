package com.srgi.controller;

import com.srgi.model.CategoriaRequerimiento;
import com.srgi.model.Requerimiento;
import com.srgi.service.CategoriaRequerimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoriaRequerimientoController {
    @Autowired
    CategoriaRequerimientoService categoriaRequerimientoService;

    @PostMapping("/registrarCategoriaRequerimiento")
    public void registrarCategoriaRequerimiento(@RequestBody CategoriaRequerimiento categoriaRequerimiento){
        categoriaRequerimientoService.registrarCategoriaRequerimiento(categoriaRequerimiento);
    }

    @GetMapping("verTodosCategoriaRequerimiento")
    public List<CategoriaRequerimiento> verTodosCategoriaRequerimiento(){
        return categoriaRequerimientoService.verTodos();
    }

}
