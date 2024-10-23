package com.srgi.service.comentario;

import com.srgi.dto.ComentarioDTO;
import com.srgi.model.Comentario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {
    List<Comentario> addComentarios(List<ComentarioDTO> comentarios, Integer idUsuario, Integer idRequerimiento, List<MultipartFile> archivos);
    Comentario getComentarioById(Integer id);
    void deleteComentario(Integer id);
    List<Comentario> getComentarios();
}
