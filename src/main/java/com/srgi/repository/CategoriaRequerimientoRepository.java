package com.srgi.repository;

import com.srgi.model.CategoriaRequerimiento;
import com.srgi.model.TipoRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoriaRequerimientoRepository extends JpaRepository<CategoriaRequerimiento, Integer> {
    List<CategoriaRequerimiento> findAllByTipoRequerimiento(TipoRequerimiento tipoRequerimiento);

    List<CategoriaRequerimiento> findAllByTipoRequerimiento_Codigo(String codigoRequerimiento);
}
