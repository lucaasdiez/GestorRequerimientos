package com.srgi.service.requerimiento;

import com.srgi.dto.*;
import com.srgi.enums.EstadoEnum;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.*;
import com.srgi.repository.RequerimientoRepository;
import com.srgi.repository.TipoRequerimientoRepository;
import com.srgi.repository.UsuarioRepository;
import com.srgi.service.archivo.ArchivoService;
import com.srgi.service.email.EmailService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RequerimientoServiceImp implements RequerimientoService{
    public final RequerimientoRepository requerimientoRepository;
    public final UsuarioRepository usuarioRepository;
    private final TipoRequerimientoRepository tipoRequerimientoRepository;
    private final ArchivoService archivoService;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Requerimiento> verTodos(){
        return requerimientoRepository.findAll();
    }

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

        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.now();
        Requerimiento requerimiento = Requerimiento.builder()
                .fechaAlta(fecha)
                .horaAlta(hora)
                .descripcion(requerimientoDTO.getDescripcion())
                .estado(EstadoEnum.ABIERTO)
                .tipoRequerimiento(tipoRequerimiento)
                .categRequerimiento(requerimientoDTO.getCategRequerimiento())
                .emisor(emisor)
                .usuarioPropietario(propietario)
                .prioridad(requerimientoDTO.getPrioridad())
                .asunto(requerimientoDTO.getAsunto())
                .build();
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
        enviarCorreoNotificacion(requerimiento);
        return requerimientoRepository.save(requerimiento);

    }


    @Override
    public Requerimiento getRequerimientoById(Integer id_requerimiento) {
        return requerimientoRepository.findById(id_requerimiento)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
    }

    @Override
    public List<Requerimiento> getRequerimientosByPropietarioId(Integer id) {
        return requerimientoRepository.findAllByEmisorId(id)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
    }


    //Verificar si esta bien
    @Override
    public void cerrarRequerimiento(String codigo) {
        Requerimiento requerimiento = requerimientoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
        requerimiento.setEstado(EstadoEnum.CERRADO);
        requerimiento.setUsuarioPropietario(null);
        requerimiento.setFechaCierre(LocalDate.now());
        requerimientoRepository.save(requerimiento);
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Requerimiento> cq = cb.createQuery(Requerimiento.class);
        Root<Requerimiento> root= cq.from(Requerimiento.class);
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
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Requerimiento> query = entityManager.createQuery(cq);
        return query.getResultList();
    }


    private void enviarCorreoNotificacion(Requerimiento requerimiento) {
        String destinatario = requerimiento.getEmisor().getEmail();
        String asunto = String.format("Nuevo requerimiento %s registrado en el sistema", requerimiento.getCodigo());
        String cuerpo = String.format("""
        <p>Estimado/a %s,</p>
        <p>Su requerimiento ha sido dado de alta en el sistema con los siguientes detalles:</p>
        <ul>
            <li><strong>Código:</strong> %s</li>
            <li><strong>Tipo:</strong> %s</li>
            <li><strong>Categoria:</strong> %s</li>
            <li><strong>Asunto:</strong> %s</li>
            <li><strong>Descripción:</strong> %s</li>
        </ul>
        <p>Gracias por usar nuestro sistema.</p>
        """,
                requerimiento.getEmisor().getNombre(),
                requerimiento.getCodigo(),
                requerimiento.getTipoRequerimiento().getCodigo(),
                requerimiento.getCategRequerimiento(),
                requerimiento.getAsunto(),
                requerimiento.getDescripcion());

        emailService.enviarCorreo(destinatario, asunto, cuerpo);
    }

    private String generarCodigo(TipoRequerimiento tipoRequerimiento, Integer idRequerimiento) {
        LocalDate fecha = LocalDate.now();
        int anio = fecha.getYear();
        String codigoContador = String.format("%011d", idRequerimiento);
        return tipoRequerimiento.getCodigo() +"-" + anio + "-" + codigoContador;

    }
}
