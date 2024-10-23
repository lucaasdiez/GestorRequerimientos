package com.srgi.repository;

import com.srgi.model.CategoriaRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRequerimientoRepository extends JpaRepository<CategoriaRequerimiento, Integer> {
    CategoriaRequerimiento findByDescripcion(String descripcion);
}
