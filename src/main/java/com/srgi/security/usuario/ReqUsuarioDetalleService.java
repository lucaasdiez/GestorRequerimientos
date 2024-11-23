package com.srgi.security.usuario;

import com.srgi.model.Usuario;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReqUsuarioDetalleService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return ReqUsuarioDetalle.buildUsuarioDetalle(usuario);
    }
}
