package com.srgi.service.tipoRequerimiento;

import com.srgi.dto.CategoriaRequerimientoDTO;
import com.srgi.dto.TipoRequerimientoDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.TipoRequerimiento;
import com.srgi.repository.TipoRequerimientoRepository;
import com.srgi.service.categoriaRequerimiento.CategRequerimientoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoRequerimientoServiceImp implements TipoRequerimientoService {
    private final TipoRequerimientoRepository tipoRequerimientoRepository;
    private final ModelMapper modelMapper;
    private final CategRequerimientoService categRequerimientoService;


    @Override
    public List<TipoRequerimientoDTO> getTipoRequerimiento() {
        List<TipoRequerimiento> tipoRequerimientos= tipoRequerimientoRepository.findAll();
        return convertirADTOS(tipoRequerimientos);
    }

    @Override
    public List<TipoRequerimientoDTO> getTipoRequerimientoByDesactivado(boolean desactivado) {
        List<TipoRequerimiento> tipoRequerimientos= tipoRequerimientoRepository.findAllByDesactivado(desactivado);
        return convertirADTOS(tipoRequerimientos);
    }

    @Override
    public void registrarTipoRequerimiento(TipoRequerimientoDTO tipoRequerimiento) {
        TipoRequerimiento nuevoTipo = TipoRequerimiento.builder()
                .descripcion(tipoRequerimiento.getDescripcion())
                .codigo(tipoRequerimiento.getCodigo())
                .desactivado(false)
                .build();
        tipoRequerimientoRepository.save(nuevoTipo);

    }

    @Override
    public TipoRequerimientoDTO getTipoRequerimiento(String codigo) {
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo requerimiento no encontrado"));
        return convertirADTO(tipoRequerimiento);
    }

    @Override
    public void updateTipoRequerimiento(TipoRequerimientoDTO tipoRequerimiento, String codigo) {
        TipoRequerimiento tipoRequerimientoViejo = tipoRequerimientoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo requerimiento no encontrado"));
        tipoRequerimientoViejo.setCodigo(tipoRequerimiento.getCodigo());
        tipoRequerimientoViejo.setDescripcion(tipoRequerimiento.getDescripcion());
        tipoRequerimientoRepository.save(tipoRequerimientoViejo);

    }

    @Override
    public void eliminarTipoRequerimiento(String codigo) {
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo requerimiento no encontrado"));
        tipoRequerimiento.setDesactivado(true);
        tipoRequerimientoRepository.save(tipoRequerimiento);
    }

    @Override
    public void reactivarTipoRequerimiento(String codigo) {
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo requerimiento no encontrado"));
        tipoRequerimiento.setDesactivado(false);
        tipoRequerimientoRepository.save(tipoRequerimiento);
    }


    private TipoRequerimientoDTO convertirADTO(TipoRequerimiento tipoRequerimiento) {
        TipoRequerimientoDTO tipoRequerimientoDTO = modelMapper.map(tipoRequerimiento, TipoRequerimientoDTO.class);
        List<CategoriaRequerimientoDTO> categoriaRequerimientoDTO = categRequerimientoService.convertirADTOs(tipoRequerimiento.getCategoriaRequerimiento());
        tipoRequerimientoDTO.setCategoriaRequerimientos(categoriaRequerimientoDTO);
        return tipoRequerimientoDTO;
    }

    private List<TipoRequerimientoDTO> convertirADTOS(List<TipoRequerimiento> tipoRequerimientoDTO) {
        return  tipoRequerimientoDTO.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
