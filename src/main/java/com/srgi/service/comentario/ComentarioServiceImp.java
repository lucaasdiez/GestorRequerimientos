package com.srgi.service.comentario;

import com.srgi.dto.ArchivoDTO;
import com.srgi.dto.ComentarioDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Comentario;
import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;
import com.srgi.repository.ComentarioRepository;
import com.srgi.repository.RequerimientoRepository;
import com.srgi.repository.UsuarioRepository;
import com.srgi.service.archivo.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImp implements ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final RequerimientoRepository requerimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ArchivoService archivoService;

    private final ModelMapper modelMapper;

    @Override
    public Comentario getComentarioById(Integer id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Comentario no encontrado"));
    }

    //Verificar Si esta bien realizado
    @Override
    @Lazy
    public List<Comentario> addComentarios(List<ComentarioDTO> comentarios, Integer idUsuario, Integer idRequerimiento, List<MultipartFile> archivos) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        Requerimiento requerimiento = requerimientoRepository.findById(idRequerimiento)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));

        List<Comentario> comentariosGuardados = null;
        if (requerimiento.getEstado().equals("Asignado")) {
            comentariosGuardados = new ArrayList<>();
            int maximo = 5;
            int indice = 0;
            for (ComentarioDTO comentarioDTO : comentarios) {
                Comentario comentario = new Comentario();
                comentario.setAsunto(comentarioDTO.getAsunto());
                comentario.setDescripcion(comentarioDTO.getDescripcion());
                comentario.setFecha(comentarioDTO.getFecha());
                comentario.setHora(comentarioDTO.getHora());
                comentario.setUsuario(usuario);
                comentario.setRequerimiento(requerimiento);

                if(archivos != null && archivos.size() > maximo && !archivos.get(indice).isEmpty()) {
                    List<ArchivoDTO> archivoGuardado = archivoService.guardarArchivo(archivos, comentario,requerimiento);
                    comentario.setArchivos(archivoGuardado.stream().map(ArchivoDTO::toEntity).collect(Collectors.toList()));
                }
                Comentario comentarioGuardado =comentarioRepository.save(comentario);
                comentariosGuardados.add(comentarioGuardado);
                indice++;

            }
            return comentariosGuardados;
        }else {
            //El requerimiento se encuentra en estado Cerrado
            return null;
        }

    }

    public List<ComentarioDTO> convertirComentarioADTO(List<Comentario> comentarios) {
        return comentarios.stream()
                .map(comentario -> modelMapper.map(comentario, ComentarioDTO.class))
                .toList();
    }

    @Override
    public void deleteComentario(Integer id) {
        comentarioRepository.findById(id).ifPresentOrElse(comentarioRepository :: delete, () -> {
            throw new ResourceNotFoundExeption("Comentario no encontrado");
        });
    }

    @Override
    public List<Comentario> getComentarios() {
        return comentarioRepository.findAll();
    }
}
