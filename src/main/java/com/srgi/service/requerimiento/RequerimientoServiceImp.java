package com.srgi.service.requerimiento;

import com.srgi.dto.*;
import com.srgi.enums.EstadoEnum;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.*;
import com.srgi.repository.RequerimientoRepository;
import com.srgi.repository.TipoRequerimientoRepository;
import com.srgi.repository.UsuarioRepository;
import com.srgi.service.archivo.ArchivoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequerimientoServiceImp implements RequerimientoService{
    public final RequerimientoRepository requerimientoRepository;
    public final UsuarioRepository usuarioRepository;
    private final TipoRequerimientoRepository tipoRequerimientoRepository;
    private final ArchivoService archivoService;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Requerimiento registrarRequerimiento(RequerimientoDTO requerimientoDTO,List<MultipartFile> files) {
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findByCodigo(requerimientoDTO.getTipoRequerimiento().getCodigo())
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo de requerimiento no encontrado"));
        Usuario propietario = null;
        if (requerimientoDTO.getPropietario() != null) {
            propietario = usuarioRepository.findById(requerimientoDTO.getPropietario().getId())
                    .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        }
        Usuario emisor = usuarioRepository.findById(requerimientoDTO.getEmisor().getId())
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));

        Requerimiento requerimiento = new Requerimiento();
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        requerimiento.setFechaAlta(fecha);
        requerimiento.setHoraAlta(hora);
        requerimiento.setDescripcion(requerimientoDTO.getDescripcion());
        requerimiento.setEstado(EstadoEnum.ABIERTO);
        requerimiento.setTipoRequerimiento(tipoRequerimiento);
        requerimiento.setEmisor(emisor);
        requerimiento.setUsuarioPropietario(propietario);
        requerimiento.setPrioridad(requerimientoDTO.getPrioridad());
        requerimiento.setAsunto(requerimientoDTO.getAsunto());

        Requerimiento savedRequerimiento = requerimientoRepository.save(requerimiento);
        List<Archivo> archivos = archivoService.archivosUpload(files, savedRequerimiento.getId(), null);
        String codigo = generarCodigo(tipoRequerimiento,requerimiento.getId());
        requerimiento.setCodigo(codigo);

        requerimiento.setArchivos(archivos);

        if(requerimientoDTO.getRequerimientoRelacionado() != null){
            List<Requerimiento> relacionados = new ArrayList<>();
            for(RequerimientoDTO requerimientoRelacionado : requerimientoDTO.getRequerimientoRelacionado()){
                Requerimiento relacionado = requerimientoRepository.findById(requerimientoRelacionado.getId())
                        .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
                relacionados.add(relacionado);
            }
            requerimiento.setRequerimientosRelacionados(relacionados);
        }

        return requerimientoRepository.save(requerimiento);

    }

    private String generarCodigo(TipoRequerimiento tipoRequerimiento, Integer idRequerimiento) {
        LocalDate fecha = LocalDate.now();
        int anio = fecha.getYear();
        String codigoContador = String.format("%011d", idRequerimiento);
        return tipoRequerimiento.getCodigo() +"-" + anio + "-" + codigoContador;

    }


    @Override
    public Requerimiento getRequerimientoById(Integer id_requerimiento) {
        return requerimientoRepository.findById(id_requerimiento)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
    }

    @Override
    public List<Requerimiento> getRequerimientosByPropietarioId(Integer id) {
        return requerimientoRepository.findAllByEmisorId(id);
    }


    //Verificar si esta bien
    @Override
    public Requerimiento cerrarRequerimiento(Integer id_requerimiento, Integer fechaCierre) {
        Requerimiento requerimiento = requerimientoRepository.findById(id_requerimiento)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
        requerimiento.setEstado(EstadoEnum.CERRADO);
        requerimiento.setUsuarioPropietario(null);
        requerimientoRepository.save(requerimiento);
        return requerimiento;
    }

    @Override
    public RequerimientoDTO convertirARequerimientoDTO(Requerimiento requerimiento) {
        RequerimientoDTO req=  modelMapper.map(requerimiento, RequerimientoDTO.class);
        List<ArchivoDTO> archivosDTO = requerimiento.getArchivos().stream()
                .map(archivo -> {
                    ArchivoDTO archivoDTO = new ArchivoDTO();
                    archivoDTO.setId(archivo.getId());
                    archivoDTO.setNombre(archivo.getNombre());
                    archivoDTO.setRutaDescarga("/archivos/archivo/descargar/" + archivo.getId()); // URL del endpoint de descarga
                    return archivoDTO;
                }).toList();
        req.setArchivos(archivosDTO);
        TipoRequerimientoDTO tipoRequerimientoDTO = modelMapper.map(requerimiento.getTipoRequerimiento(), TipoRequerimientoDTO.class);
        CategoriaRequerimientoDTO categoriaRequerimientoDTO = modelMapper.map(requerimiento.getTipoRequerimiento().getCategoriaRequerimiento(), CategoriaRequerimientoDTO.class);
        tipoRequerimientoDTO.setCategoriaRequerimientos(categoriaRequerimientoDTO);
        req.setTipoRequerimiento(tipoRequerimientoDTO);
        return req;
    }

    @Override
    public List<RequerimientoDTO> convertirARequerimientosDTO(List<Requerimiento> requerimientos) {
        return requerimientos.stream()
                .map(requerimiento1 -> {
                    RequerimientoDTO requerimientoDTO = modelMapper.map(requerimiento1, RequerimientoDTO.class);

                    // Procesar archivos
                    if(requerimiento1.getArchivos() != null) {
                        List<ArchivoDTO> archivosDTO = requerimiento1.getArchivos().stream()
                                .map(archivo -> {
                                    ArchivoDTO archivoDTO = new ArchivoDTO();
                                    archivoDTO.setId(archivo.getId());
                                    archivoDTO.setNombre(archivo.getNombre());
                                    archivoDTO.setRutaDescarga("/archivos/archivo/descargar/" + archivo.getId());
                                    return archivoDTO;
                                }).toList();
                        requerimientoDTO.setArchivos(archivosDTO);
                    }

                    // Procesar tipoRequerimiento y su categoría
                    TipoRequerimientoDTO tipoRequerimientoDTO = modelMapper.map(requerimiento1.getTipoRequerimiento(), TipoRequerimientoDTO.class);
                    CategoriaRequerimientoDTO categoriaRequerimientoDTO = modelMapper.map(requerimiento1.getTipoRequerimiento().getCategoriaRequerimiento(), CategoriaRequerimientoDTO.class);
                    tipoRequerimientoDTO.setCategoriaRequerimientos(categoriaRequerimientoDTO);
                    requerimientoDTO.setTipoRequerimiento(tipoRequerimientoDTO);
                    UExternoDTO propietario = null;
                    if (requerimiento1.getUsuarioPropietario() != null) {
                        propietario = modelMapper.map(requerimiento1.getUsuarioPropietario(), UExternoDTO.class);
                    }
                    UExternoDTO emisor = modelMapper.map(requerimiento1.getEmisor(), UExternoDTO.class);
                    requerimientoDTO.setEmisor(emisor);
                    requerimientoDTO.setPropietario(propietario);

                    return requerimientoDTO;
                }).toList();
    }

    @Override
    public List<Requerimiento> getRequerimientoByFiltros(String tipoRequerimiento, String categoria, EstadoEnum estado) {
        //El CriteriaBuilder se utiliza para crear los componentes de la consulta
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //Se crea la consulta, en este caso una consulta para obtener objetos de la clase requerimiento
        CriteriaQuery<Requerimiento> cq = cb.createQuery(Requerimiento.class);
        //El root es el objeto principal de la consulta, en este caso el Requerimiento
        Root<Requerimiento> root= cq.from(Requerimiento.class);

        //Un predicado es una condicion que sera evaluada en la consulta, si un parametro es null, no se agrega a la consulta y se añade un predicado para filtrar ese campo
        List<Predicate> predicates = new ArrayList<>();
        if (tipoRequerimiento != null){
            predicates.add(cb.equal(root.join("tipoRequerimiento").get("codigo"),tipoRequerimiento));
        }
        if (categoria != null){
            predicates.add(cb.equal(root.join("tipoRequerimiento").join("categoriaRequerimiento").get("descripcion"),categoria));
        }
        if (estado != null){
            predicates.add(cb.equal(root.get("estado"),estado));
        }
        //Despues de crear todos los filtros, se combianan en una expresion de tipo AND para que se apliquen de forma conjunta
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        //Ejecuta la consulta y obtiene los resultados
        TypedQuery<Requerimiento> query = entityManager.createQuery(cq);
        return query.getResultList();
    }


}
