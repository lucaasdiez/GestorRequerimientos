package com.srgi.service.requerimiento;

import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;

import java.util.List;

public interface RequerimientoService {
    List<Requerimiento> getAllRequerimientos();
    Requerimiento getRequerimientoById(Integer id);
    Requerimiento asignarRequerimiento(Integer id_requerimiento, Integer id_usuario);
    Requerimiento registrarRequerimiento(Requerimiento requerimiento);
    Requerimiento cerrarRequerimiento(Integer id_requerimiento, Integer fechaCierre);
}
