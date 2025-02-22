package com.srgi.service.comentario;

import com.srgi.dto.ComentarioDTO;
import com.srgi.enums.EstadoEnum;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    public Comentario addComentario(ComentarioDTO comentarioDTO, String req_codigo, List<MultipartFile> files) {
        Usuario usuario = usuarioRepository.findByUsername(comentarioDTO.getUsername());
        Requerimiento requerimiento = requerimientoRepository.findByCodigo(req_codigo);

        if (requerimiento.getEstado().equals(EstadoEnum.ABIERTO)) { // El estado debe ser asignado, pero al no tener un usuario asignado lo dejamos en abierto para evitar errores
            Comentario comentario = new Comentario();
            comentario.setAsunto(comentarioDTO.getAsunto());
            comentario.setDescripcion(comentarioDTO.getDescripcion());
            comentario.setFecha(LocalDate.now());
            comentario.setHora(LocalTime.now());
            comentario.setUsuario(usuario);
            comentario.setRequerimiento(requerimiento);
            Comentario comentarioSaved = comentarioRepository.save(comentario);
            if(files!=null){
                if (!files.isEmpty()) {
                    List<Archivo> archivoGuardado = archivoService.archivosUpload(files, requerimiento.getId(), comentarioSaved.getId());
                    comentario.setArchivos(archivoGuardado);
                }
            }
            return comentarioRepository.save(comentario);
        }else {
            throw new RuntimeException("El estado no es valido");
        }

    }
    public ComentarioDTO convertirComentarioADTO(Comentario comentario) {
        ComentarioDTO comentarioDTO =  modelMapper.map(comentario, ComentarioDTO.class);
        comentarioDTO.setUsername(comentario.getUsuario().getUsername());
        return comentarioDTO;
    }

    @Override
    public List<ComentarioDTO> convertirAComentariosDTO(List<Comentario> comentarios) {
        return comentarios.stream()
                .map(this::convertirComentarioADTO)
                .toList();
    }


    @Override
    public List<Comentario> getComentarios() {
        return comentarioRepository.findAll();
    }

    @Override
    public List<Comentario> getComentariosByCodigoRequerimiento(String codigo) {
        return comentarioRepository.findALlByRequerimiento_Codigo(codigo);
    }


}
