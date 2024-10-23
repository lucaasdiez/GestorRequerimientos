package com.srgi.service.evento;

import com.srgi.dto.EventoDTO;
import com.srgi.dto.RequerimientoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Evento;
import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;
import com.srgi.repository.EventoRepository;
import com.srgi.repository.RequerimientoRepository;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoServiceImp implements EventoService {
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RequerimientoRepository requerimientoRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<Evento> getEventos() {
        return eventoRepository.findAll();
    }

    @Override
    public EventoDTO crearEvento( Integer id_usuario, Integer id_requerimiento, String accion) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        Requerimiento requerimiento = requerimientoRepository.findById(id_requerimiento)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
        Evento evento = new Evento();
        evento.setUsuario(usuario);
        evento.setRequerimiento(requerimiento);
        evento.setHora(LocalTime.now());
        evento.setFecha(LocalDate.now());
        evento.setAccion(accion);
        eventoRepository.save(evento);
        return convertirAEventoDTO(evento);
    }

    //Verificar si esto funciona
    @Override
    public List<Evento> getEventosByRequerimiento(Requerimiento requerimiento) {
        return eventoRepository.findByRequerimientoId(requerimiento.getId());
    }

    public EventoDTO convertirAEventoDTO(Evento evento) {
        EventoDTO eventoDTO=  modelMapper.map(evento, EventoDTO.class);
        if (evento.getUsuario() != null) {
            UsuarioDTO usuarioDTO = modelMapper.map(evento.getUsuario(), UsuarioDTO.class);
            eventoDTO.setUsuario(usuarioDTO);
        }

        if (evento.getRequerimiento() != null) {
            RequerimientoDTO requerimientoDTO = modelMapper.map(evento.getRequerimiento(), RequerimientoDTO.class);
            eventoDTO.setRequerimiento(requerimientoDTO);
        }

        return eventoDTO;
    }

}
