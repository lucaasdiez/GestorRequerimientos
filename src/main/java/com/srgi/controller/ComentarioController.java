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
@CrossOrigin("*")
@RequestMapping("/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @GetMapping("/{codigoRequerimiento}/todos")
    public ResponseEntity<ApiResponse> getComentariosByRequerimiento(@PathVariable String codigoRequerimiento) {
            List<Comentario> comentarios = comentarioService.getComentariosByCodigoRequerimiento(codigoRequerimiento);
            List<ComentarioDTO> comentariosDTO = comentarioService.convertirAComentariosDTO(comentarios);
            return ResponseEntity.ok(new ApiResponse("Comentarios", comentariosDTO));
    }

    @GetMapping("/comentario/{id}")
    public ResponseEntity<ApiResponse> getComentarioById(@PathVariable Integer id) {
            Comentario comentario = comentarioService.getComentarioById(id);
            ComentarioDTO comentarioDTO = comentarioService.convertirComentarioADTO(comentario);
            return ResponseEntity.ok(new ApiResponse("Comentario", comentarioDTO));
    }

    @PostMapping("/{codigo_requerimiento}/agregar")
    public ResponseEntity<ApiResponse> crearComentario(
            @PathVariable String codigo_requerimiento,
            @RequestPart(value = "comentarioDTO") ComentarioDTO comentarioDTO,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> files
    ) {
            Comentario comentario = comentarioService.addComentario(comentarioDTO,codigo_requerimiento, files );
            ComentarioDTO comentarioDTO1 = comentarioService.convertirComentarioADTO(comentario);
            return ResponseEntity.ok(new ApiResponse("Comentario", comentarioDTO1));
    }

}
