package com.srgi.service;

import com.srgi.model.CategoriaRequerimiento;
import com.srgi.repository.CategoriaRequerimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaRequerimientoService {
    @Autowired
    CategoriaRequerimientoRepository categoriaRequerimientoRepository;

    public void registrarCategoriaRequerimiento(CategoriaRequerimiento categoriaRequerimiento){
        categoriaRequerimientoRepository.save(categoriaRequerimiento);
    }

    public List<CategoriaRequerimiento> verTodos(){
        return categoriaRequerimientoRepository.findAll();
    }
}
