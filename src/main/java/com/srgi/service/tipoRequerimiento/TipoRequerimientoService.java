package com.srgi.service.tipoRequerimiento;

import com.srgi.dto.TipoRequerimientoDTO;
import com.srgi.model.TipoRequerimiento;

import java.util.List;

public interface TipoRequerimientoService {
    List<TipoRequerimiento> getTipoRequerimiento();

    void registrarTipoRequerimiento(TipoRequerimiento tipoRequerimiento);
}
