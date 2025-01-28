package com.srgi.service;
import com.srgi.model.*;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEntityService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> findByEmail(String email) {
        Usuario user = usuarioRepository.findByEmail(email);
        if (user != null){
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
