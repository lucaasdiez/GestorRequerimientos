package com.srgi.repository;

import com.srgi.model.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequerimientoRepository extends JpaRepository<Requerimiento, Integer> {
    Optional<List<Requerimiento>> findAllByEmisorId(Integer id);

    Optional<Requerimiento> findByCodigo(String codigo);
}
