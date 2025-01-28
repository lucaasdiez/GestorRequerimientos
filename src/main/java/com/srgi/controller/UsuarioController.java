package com.srgi.controller;

import com.srgi.dto.UExternoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.exeptions.AlreadyExistExeption;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.UExterno;
import com.srgi.model.Usuario;
import com.srgi.response.ApiResponse;
import com.srgi.service.usuario.UsuarioService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            List<UsuarioDTO> usuarioDTOS = usuarioService.convertirAUsuariosDTO(usuarios);
            return ResponseEntity.ok(new ApiResponse("Usuarios", usuarioDTOS));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ApiResponse> getUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.getUsuarioById(id);
            UsuarioDTO usuarioDTO = usuarioService.convertirAUsuarioDTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario", usuarioDTO));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse> registrarUsuario(@RequestBody UExternoDTO usuarioDTO) {
        try{
            Usuario usuario = usuarioService.registrarUsuario(usuarioDTO);
            UExternoDTO uDTO = usuarioService.convertirAUsuarioDTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario registrado", uDTO));
        }catch (AlreadyExistExeption e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateUsuario(@PathVariable Integer id, @RequestBody UExternoDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.updateUsuario(id, usuarioDTO);
            UsuarioDTO uDTO = usuarioService.convertirAUsuarioDTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario actualizado", uDTO));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<ApiResponse> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok(new ApiResponse("Usuario eliminado", null));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
