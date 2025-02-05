package com.srgi.controller;

import com.srgi.model.CategoriaRequerimiento;
import com.srgi.model.Requerimiento;
import com.srgi.model.TipoRequerimiento;
import com.srgi.service.CategoriaRequerimientoService;
import com.srgi.service.tipoRequerimiento.TipoRequerimientoServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TipoRequerimientoController {
    @Autowired
    TipoRequerimientoServiceImp tipoRequerimientoServiceImp;

    @PostMapping("/registrarTipoRequerimiento")
    public void registrarTipoRequerimiento(@RequestBody TipoRequerimiento tipoRequerimiento){
        tipoRequerimientoServiceImp.registrarTipoRequerimiento(tipoRequerimiento);
    }

    @GetMapping("verTodosTipoRequerimiento")
    public List<TipoRequerimiento> verTodosTipoRequerimiento(){
        return tipoRequerimientoServiceImp.getTipoRequerimiento();
    }

}
