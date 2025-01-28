package com.srgi.service.tipoRequerimiento;

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
    public List<TipoRequerimiento> getTipoRequerimientos() {
        return tipoRequerimientoRepository.findAll();
    }
}
