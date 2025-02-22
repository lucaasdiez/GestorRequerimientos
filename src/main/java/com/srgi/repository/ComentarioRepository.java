package com.srgi.repository;

import com.srgi.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findALlByRequerimiento_Codigo(String requerimientoCodigo);
}
