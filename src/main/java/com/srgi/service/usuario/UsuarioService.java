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
    void updateUsuario(Integer id, UExternoDTO usuarioDTO);
    boolean updatePassword(Integer id, UExternoDTO usuario);
    void deleteUsuario(Integer id);
    List<UExterno> getAllUsuarios();
    UExterno registrarAdmin(UExternoDTO uExternoDTO);
   // public Usuario getUsuarioAutenticado();

    List<UExternoDTO> convertirAUsuariosDTO(List<UExterno> usuarios);

    UExternoDTO convertirAUsuarioDTO(Usuario usuario);
}
