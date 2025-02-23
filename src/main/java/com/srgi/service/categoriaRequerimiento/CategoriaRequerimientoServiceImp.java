package com.srgi.service.categoriaRequerimiento;

import com.srgi.dto.CategoriaRequerimientoDTO;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.CategoriaRequerimiento;
import com.srgi.model.TipoRequerimiento;
import com.srgi.repository.CategoriaRequerimientoRepository;
import com.srgi.repository.TipoRequerimientoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaRequerimientoServiceImp implements CategRequerimientoService {

    private final ModelMapper modelMapper;
    private final CategoriaRequerimientoRepository categoriaRequerimientoRepository;
    private final TipoRequerimientoRepository tipoRequerimientoRepository;


    public List<CategoriaRequerimientoDTO> getAll(){
        List<CategoriaRequerimiento> categReq= categoriaRequerimientoRepository.findAll();
        return convertirADTOs(categReq);
    }

    @Override
    public void updateCategoriaRequerimiento(CategoriaRequerimientoDTO categoriaRequerimiento, Integer id) {
        CategoriaRequerimiento categReqVieja = categoriaRequerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Categoria Requerimiento no encontrada"));
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findByCodigo(categoriaRequerimiento.getCodigoTipoRequerimiento())
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo de requerimiento no encontrado"));
        categReqVieja.setDescripcion(categoriaRequerimiento.getDescripcion());
        categReqVieja.setTipoRequerimiento(tipoRequerimiento);
        categoriaRequerimientoRepository.save(categReqVieja);
    }

    @Override
    public void deleteCategoriaRequerimiento(Integer id) {
        CategoriaRequerimiento categReqVieja = categoriaRequerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Categoria Requerimiento no encontrada"));
        categReqVieja.setDesactivado(true);
        categoriaRequerimientoRepository.save(categReqVieja);
    }

    @Override
    public List<CategoriaRequerimiento> getCategoriaByCodigoRequerimiento(String codigoRequerimiento) {
        return categoriaRequerimientoRepository.findAllByTipoRequerimiento_Codigo(codigoRequerimiento);
    }


    @Override
    public void registrarCategoriaRequerimiento(CategoriaRequerimientoDTO categoriaRequerimiento) {
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findByCodigo(categoriaRequerimiento.getCodigoTipoRequerimiento())
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo de requerimiento no encontrado"));
        CategoriaRequerimiento categReq = CategoriaRequerimiento.builder()
                .descripcion(categoriaRequerimiento.getDescripcion())
                .tipoRequerimiento(tipoRequerimiento)
                .build();
        categoriaRequerimientoRepository.save(categReq);
    }

    @Override
    public CategoriaRequerimientoDTO getCategoriaRequerimiento(Integer id) {
        return convertirDTO(categoriaRequerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Categoria de Requerimiento no encotrada")));
    }

    private CategoriaRequerimientoDTO convertirDTO(CategoriaRequerimiento categoriaRequerimiento) {
        return modelMapper.map(categoriaRequerimiento, CategoriaRequerimientoDTO.class);
    }

    @Override
    public List<CategoriaRequerimientoDTO> convertirADTOs(List<CategoriaRequerimiento> categReq) {
        return categReq.stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }
}
