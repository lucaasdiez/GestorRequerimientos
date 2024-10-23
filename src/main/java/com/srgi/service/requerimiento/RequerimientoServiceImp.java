package com.srgi.service.requerimiento;

import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Requerimiento;
import com.srgi.model.Usuario;
import com.srgi.repository.RequerimientoRepository;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RequerimientoServiceImp implements RequerimientoService{
    public final RequerimientoRepository requerimientoRepository;
    public final UsuarioRepository usuarioRepository;

    @Override
    public Requerimiento registrarRequerimiento(Requerimiento requerimiento) {
        return null;
    }


    @Override
    public Requerimiento getRequerimientoById(Integer id) {
        return requerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
    }

    @Override
    public Requerimiento asignarRequerimiento(Integer id_requerimiento, Integer id_usuario) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        Requerimiento requerimiento = requerimientoRepository.findById(id_requerimiento)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
        if (!requerimiento.getEstado().equals("Asignado")) {
            usuario.setRequerimientoPropietario(requerimiento);
            requerimiento.setEstado("Asignado");
            requerimiento.setUsuarioPropietario(usuario);
            usuarioRepository.save(usuario);
            requerimientoRepository.save(requerimiento);
        }
        return requerimiento;
    }

    @Override
    public Requerimiento cerrarRequerimiento(Integer id_requerimiento, Integer fechaCierre) {
        Requerimiento requerimiento = requerimientoRepository.findById(id_requerimiento)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
        requerimiento.setEstado("Cancelado");
        requerimiento.setUsuarioPropietario(null);
        requerimientoRepository.save(requerimiento);
        return requerimiento;
    }

    @Override
    public List<Requerimiento> getAllRequerimientos() {
        return requerimientoRepository.findAll();
    }


}
