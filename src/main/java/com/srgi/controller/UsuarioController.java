package com.srgi.controller;

import com.srgi.dto.AdminDTO;
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
        List<UExterno> usuarios = usuarioService.getAllUsuarios();
        List<UExternoDTO> usuarioDTOS = usuarioService.convertirAUsuariosDTO(usuarios);
        return ResponseEntity.ok(new ApiResponse("Usuarios", usuarioDTOS));
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ApiResponse> getUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        UsuarioDTO usuarioDTO = usuarioService.convertirAUsuarioDTO(usuario);
        return ResponseEntity.ok(new ApiResponse("Usuario", usuarioDTO));
    }

    @GetMapping("/todos/{estadoCuenta}")
    public ResponseEntity<ApiResponse> getUsuarioByEstadoCuenta(@PathVariable boolean estadoCuenta) {
        List<UExterno> uExternos = usuarioService.getAllUsuariosByEstado(estadoCuenta);
        List<UExternoDTO> uExternosDTO = usuarioService.convertirAUsuariosDTO(uExternos);
        return ResponseEntity.ok(new ApiResponse("Usuarios encontrados", uExternosDTO));
    }

    @GetMapping("/usuario/{username}")
    public ResponseEntity<ApiResponse> getUsuarioByUsername(@PathVariable String username) {
        UExterno uExterno = usuarioService.getUsuarioByUsername(username);
        UExternoDTO userDTO = usuarioService.convertirAUsuarioDTO(uExterno);
        return ResponseEntity.ok(new ApiResponse("Usuario encontrado", userDTO));
    }


    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse> registrarUsuario(@RequestBody UExternoDTO usuarioDTO) {
        Usuario usuario = usuarioService.registrarUsuario(usuarioDTO);
        UExternoDTO uDTO = usuarioService.convertirAUsuarioDTO(usuario);
        return ResponseEntity.ok(new ApiResponse("Usuario registrado", uDTO));
    }

    @PostMapping("/registrarAdmin")
    public ResponseEntity<ApiResponse> registrarAdmin(@RequestBody AdminDTO adminDTO) {
        UExterno admin = usuarioService.registrarAdmin(adminDTO);
        return ResponseEntity.ok(new ApiResponse("Admin creado con exito",admin));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateUsuario(@PathVariable Integer id, @RequestBody UExternoDTO usuarioDTO) {
        usuarioService.updateUsuario(id, usuarioDTO);
        return ResponseEntity.ok(new ApiResponse("Usuario actualizado", null));
    }

    @PatchMapping("/{username}/updatePassword")
    public ResponseEntity<ApiResponse> updatePassword(@PathVariable String username, @RequestParam String password) {
        usuarioService.updatePassword(username, password);
        return ResponseEntity.ok(new ApiResponse("Contraseña actualizada", null));
    }

    @PatchMapping("/{id}/eliminar")
    public ResponseEntity<ApiResponse> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok(new ApiResponse("Usuario eliminado", null));
    }

    @PatchMapping("/{username}/reactivar")
    public ResponseEntity<ApiResponse> reactivarUsuario(@PathVariable String username) {
        usuarioService.reactivarUsuario(username);
        return ResponseEntity.ok(new ApiResponse("Usuario reactivado", null));
    }


}
