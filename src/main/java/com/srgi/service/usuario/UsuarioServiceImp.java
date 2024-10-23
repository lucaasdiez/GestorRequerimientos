package com.srgi.service.usuario;

import com.srgi.dto.UEmpresaDTO;
import com.srgi.dto.UExternoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.UEmpresa;
import com.srgi.model.UExterno;
import com.srgi.model.Usuario;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Usuario no Encontrado"));
    }

    @Override
    public Usuario registrarUsuario(UExternoDTO usuarioDTO) {
        UExterno uExterno = new UExterno();
        uExterno.setNombre(usuarioDTO.getNombre());
        uExterno.setApellido(usuarioDTO.getApellido());
        uExterno.setEmail(usuarioDTO.getEmail());
        uExterno.setPassword(usuarioDTO.getPassword()); //Habria que cifrarla?
        uExterno.setEmail(usuarioDTO.getEmail());
        uExterno.setCuil(usuarioDTO.getCuil());
        uExterno.setDescripcion(usuarioDTO.getDescripcion());
        uExterno.setEmpresa(usuarioDTO.getEmpresa());
        return usuarioRepository.save(uExterno);

    }

    @Override
    public Usuario updateUsuario(Integer id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> updateUsuarioExistente(usuarioExistente, usuarioDTO))
                .map(usuarioRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
    }

    private Usuario updateUsuarioExistente(Usuario usuarioExistente, UsuarioDTO usuarioDTO) {
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setEmail(usuarioDTO.getEmail());

        return usuarioExistente;
    }

    public UEmpresaDTO convertirAEmpresaDTO(Usuario usuarioExistente, UsuarioDTO usuarioDTO) {
        UEmpresaDTO uempresaDTO = new UEmpresaDTO();
        uempresaDTO.setNombre(usuarioDTO.getNombre());
        uempresaDTO.setApellido(usuarioDTO.getApellido());
        uempresaDTO.setEmail(usuarioDTO.getEmail());
        //uempresaDTO.setLegajo();
        return uempresaDTO;
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
}
