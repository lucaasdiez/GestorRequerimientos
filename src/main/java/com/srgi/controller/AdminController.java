package com.srgi.controller;

import com.srgi.dto.AdminDTO;
import com.srgi.response.ApiResponse;
import com.srgi.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    private final UsuarioService usuarioService;

    @PostMapping("/registrarAdmin")
    public ResponseEntity<ApiResponse> registrarAdmin(@RequestBody AdminDTO adminDTO) {
        AdminDTO admin = usuarioService.registrarAdmin(adminDTO);
        return ResponseEntity.ok(new ApiResponse("Admin creado con exito",admin));
    }

    @GetMapping("/{username}/adminDetalle")
    public ResponseEntity<ApiResponse> getAdminDetalle(@PathVariable String username) {
        AdminDTO adminDTO = usuarioService.getAdminByUsername(username);
        return ResponseEntity.ok(new ApiResponse("Admin encontrado",adminDTO));
    }
}
