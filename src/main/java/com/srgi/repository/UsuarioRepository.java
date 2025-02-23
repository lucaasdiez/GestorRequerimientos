package com.srgi.repository;

import com.srgi.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmail(String email);
    Usuario findByUsername(String username);

    boolean existsByUsername(String username);

}
