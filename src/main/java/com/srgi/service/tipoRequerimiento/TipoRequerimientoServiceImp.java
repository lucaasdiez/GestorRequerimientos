package com.srgi.service.tipoRequerimiento;

import com.srgi.dto.TipoRequerimientoDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.TipoRequerimiento;
import com.srgi.repository.TipoRequerimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoRequerimientoServiceImp implements TipoRequerimientoService {
    private final TipoRequerimientoRepository tipoRequerimientoRepository;

    @Override
    public TipoRequerimiento addTipoRequerimiento(TipoRequerimientoDTO tipoRequerimientoDTO) {
        TipoRequerimiento tipoRequerimiento = new TipoRequerimiento();
        tipoRequerimiento.setDescripcion(tipoRequerimientoDTO.getDescripcion());
        tipoRequerimiento.setCodigo(tipoRequerimientoDTO.getCodigo());
        tipoRequerimiento.setCategoriasRequerimientos(tipoRequerimientoDTO.getCategoriaRequerimientos());
        tipoRequerimientoRepository.save(tipoRequerimiento);
        return tipoRequerimiento;
    }

    @Override
    public TipoRequerimiento updateTipoRequerimiento(TipoRequerimientoDTO tipoRequerimientoDTO, Integer id) {
        return tipoRequerimientoRepository.findById(id)
                .map(tipoRequerimientoExistente -> updateTipoRequerimientoExistente(tipoRequerimientoExistente, tipoRequerimientoDTO))
                .map(tipoRequerimientoRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundExeption("TipoRequerimiento no encontrado"));
    }

    private TipoRequerimiento updateTipoRequerimientoExistente(TipoRequerimiento tipoRequerimientoExistente, TipoRequerimientoDTO tipoRequerimientoDTO) {
        tipoRequerimientoExistente.setCodigo(tipoRequerimientoDTO.getCodigo());
        tipoRequerimientoExistente.setDescripcion(tipoRequerimientoDTO.getDescripcion());
        tipoRequerimientoExistente.setCategoriasRequerimientos(tipoRequerimientoDTO.getCategoriaRequerimientos());
        return tipoRequerimientoExistente;
    }

    @Override
    public void deleteTipoRequerimiento(Integer id) {
        tipoRequerimientoRepository.findById(id).ifPresentOrElse(tipoRequerimientoRepository::delete, () ->{
            throw new ResourceNotFoundExeption("TipoRequerimiento no encontrado");
        });
    }

    @Override
    public List<TipoRequerimiento> getTipoRequerimientos() {
        return tipoRequerimientoRepository.findAll();
    }
}
