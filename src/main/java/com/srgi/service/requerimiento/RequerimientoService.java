package com.srgi.service.requerimiento;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;

import java.util.List;

public interface RequerimientoService {

    List<Requerimiento> getRequerimientosById(Integer id);
    Requerimiento registrarRequerimiento(RequerimientoDTO requerimientoDTO );
    Requerimiento cerrarRequerimiento(Integer id_requerimiento, Integer fechaCierre);
    List<RequerimientoDTO> convertirARequerimientoDTO(List<Requerimiento> requerimiento);
    List<Requerimiento> getRequerimientoByFiltros(String tipo , String categoria, String estado);

}
