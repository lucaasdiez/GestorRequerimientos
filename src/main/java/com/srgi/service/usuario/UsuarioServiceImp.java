package com.srgi.service.usuario;

import com.srgi.dto.UEmpresaDTO;
import com.srgi.dto.UExternoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.exeptions.AlreadyExistExeption;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.UExterno;
import com.srgi.model.Usuario;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Usuario no Encontrado"));
    }

    @Override
    public Usuario registrarUsuario(UExternoDTO usuarioDTO) {
        String encodedPass = passwordEncoder.encode(usuarioDTO.getPassword());
        return Optional.of(usuarioDTO)
                .filter(usuari -> !usuarioRepository.existsByEmail(usuarioDTO.getEmail()))
                .map(usuariodto-> {
                    UExterno uExterno = new UExterno();
                    uExterno.setNombre(usuariodto.getNombre());
                    uExterno.setApellido(usuariodto.getApellido());
                    uExterno.setEmail(usuariodto.getEmail());
                    uExterno.setPassword(encodedPass);
                    uExterno.setEmail(usuariodto.getEmail());
                    uExterno.setRole("ROLE_USUARIOEXTERNO");
                    uExterno.setCuil(usuariodto.getCuil());
                    uExterno.setDescripcion(usuariodto.getDescripcion());
                    uExterno.setEmpresa(usuariodto.getEmpresa());
                    return usuarioRepository.save(uExterno);
                }).orElseThrow(()-> new AlreadyExistExeption("Usuario con email" + usuarioDTO.getEmail() +"ya existe"));

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

    public UEmpresaDTO convertirAEmpresaDTO(Usuario usuario) {
        return modelMapper.map(usuario, UEmpresaDTO.class);
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.findById(id).ifPresentOrElse(usuarioRepository::delete, ()-> {
            throw new ResourceNotFoundExeption("Usuario no encontrado");
        });
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

  //  @Override
 //   public Usuario getUsuarioAutenticado() {
 //       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //      String email = authentication.getName();
   //     return usuarioRepository.findByEmail(email);
   // }

    @Override
    public List<UsuarioDTO> convertirAUsuariosDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .toList();
    }

    @Override
    public UExternoDTO convertirAUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UExternoDTO.class);
    }
}
