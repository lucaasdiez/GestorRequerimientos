package com.srgi.service.requerimiento;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.enums.EstadoEnum;
import com.srgi.model.Requerimiento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RequerimientoService {

    List<RequerimientoDTO> verTodos();
    Requerimiento getRequerimientoById(Integer id);
    List<Requerimiento> getRequerimientosByPropietarioId(Integer id);
    Requerimiento registrarRequerimiento(RequerimientoDTO requerimientoDTO,List<MultipartFile> files );
    void cerrarRequerimiento(String codigo);
    List<RequerimientoDTO> convertirARequerimientosDTO(List<Requerimiento> requerimiento);
    RequerimientoDTO convertirARequerimientoDTO(Requerimiento requerimientoDTO);
    List<Requerimiento> getRequerimientoByFiltros(String tipo , String categoria, EstadoEnum estado, String username, String prioridad);

}
