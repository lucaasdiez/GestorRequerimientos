package com.srgi.repository;

import com.srgi.model.Requerimiento;
import com.srgi.model.TipoRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TipoRequerimientoRepository extends JpaRepository<TipoRequerimiento, Integer> {
    Optional<TipoRequerimiento> findByCodigo(String codigo);

    List<TipoRequerimiento> findAllByDesactivado(boolean desactivado);
}
