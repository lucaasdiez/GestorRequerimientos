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

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse> getComentariosByRequerimiento() {
            List<Comentario> comentarios = comentarioService.getComentarios();
            List<ComentarioDTO> comentariosDTO = comentarioService.convertirAComentariosDTO(comentarios);
            return ResponseEntity.ok(new ApiResponse("Comentarios", comentariosDTO));
    }

    @GetMapping("/comentario/{id}")
    public ResponseEntity<ApiResponse> getComentarioById(@PathVariable Integer id) {
            Comentario comentario = comentarioService.getComentarioById(id);
            ComentarioDTO comentarioDTO = comentarioService.convertirComentarioADTO(comentario);
            return ResponseEntity.ok(new ApiResponse("Comentario", comentarioDTO));
    }

    @PostMapping("/{requerimiento_id}/agregar")
    public ResponseEntity<ApiResponse> crearComentario(
            @PathVariable Integer requerimiento_id,
            @RequestBody ComentarioDTO comentarioDTO,
            @RequestBody List<MultipartFile> files,
            @RequestHeader("Authorization") Integer id_usuario
    ) {
            Comentario comentario = comentarioService.addComentario(comentarioDTO,requerimiento_id, id_usuario, files );
            ComentarioDTO comentarioDTO1 = comentarioService.convertirComentarioADTO(comentario);
            return ResponseEntity.ok(new ApiResponse("Comentario", comentarioDTO1));
    }

}
