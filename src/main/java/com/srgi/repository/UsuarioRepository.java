package com.srgi.repository;

import com.srgi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
    Usuario findByUsername(String username);
}
