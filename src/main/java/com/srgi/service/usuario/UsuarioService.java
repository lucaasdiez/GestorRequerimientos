package com.srgi.service.usuario;

import com.srgi.dto.UExternoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.model.Comentario;
import com.srgi.model.Requerimiento;
import com.srgi.model.UExterno;
import com.srgi.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario getUsuarioById(Integer id);
    Usuario registrarUsuario(UExternoDTO usuarioDTO );
    Usuario updateUsuario(Integer id, UsuarioDTO usuarioDTO);
    void deleteUsuario(Integer id);
    List<Usuario> getAllUsuarios();
    public Usuario getUsuarioAutenticado();

    List<UsuarioDTO> convertirAUsuariosDTO(List<Usuario> usuarios);

    UExternoDTO convertirAUsuarioDTO(Usuario usuario);
}
