package com.srgi.service.usuario;

import com.srgi.dto.UEmpresaDTO;
import com.srgi.dto.UExternoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.exeptions.AlreadyExistExeption;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.UExterno;
import com.srgi.model.Usuario;
import com.srgi.repository.UsuarioExternoRepository;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Usuario no Encontrado"));
    }

    @Override
    public Usuario registrarUsuario(UExternoDTO usuarioDTO) {
        return Optional.of(usuarioDTO)
                .filter(usuari -> !usuarioRepository.existsByEmail(usuarioDTO.getEmail()))
                .map(usuariodto-> {
                    UExterno uExterno = UExterno.builder()
                            .nombre(usuarioDTO.getNombre())
                            .apellido(usuarioDTO.getApellido())
                            .email(usuarioDTO.getEmail())
                            .username(usuarioDTO.getUsername())
                            .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                            .role("ROLE_USUARIOEXTERNO")
                            .cuil(usuarioDTO.getCuil())
                            .descripcion(usuarioDTO.getDescripcion())
                            .empresa(usuarioDTO.getEmpresa())
                            .activado(true)
                            .preferencia(usuarioDTO.isPreferencia())
                            .build();
                    return usuarioRepository.save(uExterno);
                }).orElseThrow(()-> new AlreadyExistExeption("Usuario con email " + usuarioDTO.getEmail() + " ya existe"));

    }

    @Override
    public void updateUsuario(Integer id, UsuarioDTO usuarioDTO) {
        usuarioExternoRepository.findById(id)
            .map(usuarioExistente -> updateUsuarioExistente(usuarioExistente, usuarioDTO))
            .map(usuarioRepository :: save)
            .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        return;
    }

    public UExterno registrarAdmin(UExternoDTO uExternoDTO){
        String pass = passwordEncoder.encode(uExternoDTO.getPassword());
        UExterno admin = UExterno.builder().username(uExternoDTO.getUsername()).password(pass).role("ROLE_ADMIN").build();
        return usuarioExternoRepository.save(admin);
    }

    private Usuario updateUsuarioExistente(UExterno usuarioExistente, UsuarioDTO usuarioDTO) {
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setUsername(usuarioDTO.getUsername());
        usuarioExistente.setRole(usuarioDTO.getRole());
        usuarioExistente.setActivado(usuarioDTO.isActivado());
        usuarioExistente.setCuil(usuarioDTO.getCuil());
        usuarioExistente.setDescripcion(usuarioDTO.getDescripcion());
        usuarioExistente.setEmpresa(usuarioDTO.getEmpresa());
        usuarioExistente.setPreferencia(usuarioDTO.isPreferencia());
        return usuarioExistente;
    }

    public boolean updatePassword(Integer id, UExternoDTO usuario){
        Optional<UExterno> uExternoOptional = usuarioExternoRepository.findById(id);
        if(uExternoOptional.isPresent()){
            UExterno uExterno = uExternoOptional.get();
            uExterno.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioExternoRepository.save(uExterno);
            return true;
        }else{
            return false;
        }
    }

    public UEmpresaDTO convertirAEmpresaDTO(Usuario usuario) {
        return modelMapper.map(usuario, UEmpresaDTO.class);
    }

    @Override
    public void deleteUsuario(Integer id) {
        Optional<UExterno> usuarioOptional = usuarioExternoRepository.findById(id);
        if(usuarioOptional.isPresent()){
            UExterno uExterno = usuarioOptional.get();
            uExterno.setActivado(false);
            usuarioExternoRepository.save(uExterno);
        }else{
            throw new ResourceNotFoundExeption("Usuario no encontrado");
        }
    }

    @Override
    public List<UExterno> getAllUsuarios() {
        return usuarioExternoRepository.findAll();
    }

  //  @Override
 //   public Usuario getUsuarioAutenticado() {
 //       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //      String email = authentication.getName();
   //     return usuarioRepository.findByEmail(email);
   // }

    @Override
    public List<UExternoDTO> convertirAUsuariosDTO(List<UExterno> usuarios) {
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UExternoDTO.class))
                .toList();
    }

    @Override
    public UExternoDTO convertirAUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UExternoDTO.class);
    }
}
