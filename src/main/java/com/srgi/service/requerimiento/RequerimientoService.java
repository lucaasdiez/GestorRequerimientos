package com.srgi.service.requerimiento;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;

import java.util.List;

public interface RequerimientoService {

    Requerimiento getRequerimientoById(Integer id);
    List<Requerimiento> getRequerimientosById(Integer id);
    Requerimiento registrarRequerimiento(RequerimientoDTO requerimientoDTO );
    Requerimiento cerrarRequerimiento(Integer id_requerimiento, Integer fechaCierre);
    List<RequerimientoDTO> convertirARequerimientosDTO(List<Requerimiento> requerimiento);
    RequerimientoDTO convertirARequerimientoDTO(Requerimiento requerimientoDTO);
    List<Requerimiento> getRequerimientoByFiltros(String tipo , String categoria, String estado);

}
