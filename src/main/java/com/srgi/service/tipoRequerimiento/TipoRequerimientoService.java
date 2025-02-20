package com.srgi.service.tipoRequerimiento;

import com.srgi.dto.TipoRequerimientoDTO;
import com.srgi.model.TipoRequerimiento;

import java.util.List;

public interface TipoRequerimientoService {
    List<TipoRequerimientoDTO> getTipoRequerimiento();
    List<TipoRequerimientoDTO> getTipoRequerimientoByDesactivado(boolean desactivado);
    void registrarTipoRequerimiento(TipoRequerimientoDTO tipoRequerimiento);
    TipoRequerimientoDTO getTipoRequerimiento(String codigo);
    void updateTipoRequerimiento(TipoRequerimientoDTO tipoRequerimiento, String codigo);
    void eliminarTipoRequerimiento(String codigo);
    void reactivarTipoRequerimiento(String codigo);
}
