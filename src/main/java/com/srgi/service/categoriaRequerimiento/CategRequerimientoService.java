package com.srgi.service.categoriaRequerimiento;

import com.srgi.dto.CategoriaRequerimientoDTO;
import com.srgi.model.CategoriaRequerimiento;

import java.util.List;

public interface CategRequerimientoService {
     void registrarCategoriaRequerimiento(CategoriaRequerimientoDTO categoriaRequerimiento);
     CategoriaRequerimientoDTO getCategoriaRequerimiento(Integer id);
     List<CategoriaRequerimientoDTO> getAll();
     void updateCategoriaRequerimiento(CategoriaRequerimientoDTO categoriaRequerimiento, Integer id);
     void deleteCategoriaRequerimiento(Integer id);

}
