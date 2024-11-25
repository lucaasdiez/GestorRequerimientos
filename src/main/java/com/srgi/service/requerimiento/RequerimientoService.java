package com.srgi.service.requerimiento;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.enums.EstadoEnum;
import com.srgi.model.Requerimiento;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RequerimientoService {

    Requerimiento getRequerimientoById(Integer id);
    List<Requerimiento> getRequerimientosByPropietarioId(Integer id);
    Requerimiento registrarRequerimiento(RequerimientoDTO requerimientoDTO,List<MultipartFile> files );
    Requerimiento cerrarRequerimiento(Integer id_requerimiento, Integer fechaCierre);
    List<RequerimientoDTO> convertirARequerimientosDTO(List<Requerimiento> requerimiento);
    RequerimientoDTO convertirARequerimientoDTO(Requerimiento requerimientoDTO);
    List<Requerimiento> getRequerimientoByFiltros(String tipo , String categoria, EstadoEnum estado);

}
