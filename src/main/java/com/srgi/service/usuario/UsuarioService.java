package com.srgi.service.usuario;

import com.srgi.dto.AdminDTO;
import com.srgi.dto.UExternoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.model.*;

import java.util.List;

public interface UsuarioService {
    Usuario getUsuarioById(Integer id);
    Usuario registrarUsuario(UExternoDTO usuarioDTO );
    void updateUsuario(Integer id, UExternoDTO usuarioDTO);
    void updatePassword(String username, String password);
    void deleteUsuario(Integer id);
    void reactivarUsuario(String username);
    List<UExterno> getAllUsuarios();
    List<UExterno> getAllUsuariosByEstado(boolean estado);
    UExterno getUsuarioByUsername(String username);
    Admin registrarAdmin(AdminDTO adminDTO);
   // public Usuario getUsuarioAutenticado();

    List<UExternoDTO> convertirAUsuariosDTO(List<UExterno> usuarios);

    UExternoDTO convertirAUsuarioDTO(Usuario usuario);
}
