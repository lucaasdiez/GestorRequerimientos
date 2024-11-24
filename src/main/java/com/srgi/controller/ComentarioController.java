package com.srgi.controller;

import com.srgi.dto.ComentarioDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Comentario;
import com.srgi.response.ApiResponse;
import com.srgi.service.comentario.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse> getComentariosByRequerimiento() {
        try {
            List<Comentario> comentarios = comentarioService.getComentarios();
            List<ComentarioDTO> comentariosDTO = comentarioService.convertirAComentariosDTO(comentarios);
            return ResponseEntity.ok(new ApiResponse("Comentarios", comentariosDTO));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/comentario/{id}")
    public ResponseEntity<ApiResponse> getComentarioById(@PathVariable Integer id) {
        try {
            Comentario comentario = comentarioService.getComentarioById(id);
            ComentarioDTO comentarioDTO = comentarioService.convertirComentarioADTO(comentario);
            return ResponseEntity.ok(new ApiResponse("Comentario", comentarioDTO));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/{requerimiento_id}/registar")
    public ResponseEntity<ApiResponse> createComentario(
            @PathVariable Integer requerimiento_id,
            @RequestBody ComentarioDTO comentarioDTO,
            @RequestBody List<MultipartFile> files,
            @RequestHeader("Authorization") Integer id_usuario
    ) {
        try{
            Comentario comentario = comentarioService.addComentario(comentarioDTO,requerimiento_id, id_usuario, files );
            ComentarioDTO comentarioDTO1 = comentarioService.convertirComentarioADTO(comentario);
            return ResponseEntity.ok(new ApiResponse("Comentario", comentarioDTO1));
        }catch (ResourceNotFoundExeption e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
