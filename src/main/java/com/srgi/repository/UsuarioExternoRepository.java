package com.srgi.repository;

import com.srgi.model.UExterno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioExternoRepository extends JpaRepository<UExterno, Integer> {
    Optional<UExterno> findByUsername(String username);

    List<UExterno> findAllByActivado(boolean activado);

    boolean existsByCuil(String cuil);
}
