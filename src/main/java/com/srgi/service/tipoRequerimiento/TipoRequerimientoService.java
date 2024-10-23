package com.srgi.service.tipoRequerimiento;

import com.srgi.dto.TipoRequerimientoDTO;
import com.srgi.model.TipoRequerimiento;

import java.util.List;

public interface TipoRequerimientoService {
    TipoRequerimiento addTipoRequerimiento(TipoRequerimientoDTO tipoRequerimientoDTO);
    TipoRequerimiento updateTipoRequerimiento(TipoRequerimientoDTO tipoRequerimientoDTO, Integer id);
    void deleteTipoRequerimiento(Integer id);
    List<TipoRequerimiento> getTipoRequerimientos();
}
