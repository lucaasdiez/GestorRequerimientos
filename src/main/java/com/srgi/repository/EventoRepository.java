package com.srgi.repository;

import com.srgi.model.Evento;
import com.srgi.model.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
    public List<Evento> findByRequerimientoId(int id);
}
