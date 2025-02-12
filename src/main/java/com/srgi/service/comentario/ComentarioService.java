package com.srgi.service.comentario;

import com.srgi.dto.ComentarioDTO;
import com.srgi.model.Comentario;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {
    Comentario addComentario(ComentarioDTO comentario, Integer idUsuario, Integer idRequerimiento, List<MultipartFile> files);
    Comentario getComentarioById(Integer id);
    List<Comentario> getComentarios();
    ComentarioDTO convertirComentarioADTO(Comentario comentario);

    List<ComentarioDTO> convertirAComentariosDTO(List<Comentario> comentarios);
}
