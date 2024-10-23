package com.srgi.service.evento;

import com.srgi.dto.EventoDTO;
import com.srgi.model.Evento;
import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;

import java.util.List;

public interface EventoService {
    public List<Evento> getEventos();
    public EventoDTO crearEvento(Integer id_usuario, Integer id_requerimiento, String accion);
    public List<Evento> getEventosByRequerimiento(Requerimiento requerimiento);
}
