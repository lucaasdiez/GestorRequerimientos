package com.srgi.service.requerimiento;

import com.srgi.dto.RequerimientoDTO;
import com.srgi.enums.EstadoEnum;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Archivo;
import com.srgi.model.Requerimiento;
import com.srgi.model.TipoRequerimiento;
import com.srgi.model.Usuario;
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

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RequerimientoServiceImp implements RequerimientoService{
    private static long contador = 1000000000L;
    public final RequerimientoRepository requerimientoRepository;
    public final UsuarioRepository usuarioRepository;
    private final TipoRequerimientoRepository tipoRequerimientoRepository;
    private final ArchivoService archivoService;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Requerimiento registrarRequerimiento(RequerimientoDTO requerimientoDTO) {
        TipoRequerimiento tipoRequerimiento = tipoRequerimientoRepository.findById(requerimientoDTO.getTipoRequerimiento().getIdTipoRequerimiento())
                .orElseThrow(() -> new ResourceNotFoundExeption("Tipo de requerimiento no encontrado"));
        Usuario propietario = usuarioRepository.findById(requerimientoDTO.getUsuarioPropietario().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        Usuario emisor = usuarioRepository.findById(requerimientoDTO.getEmisor().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        List<Archivo> archivos = archivoService.guardarArchivo(requerimientoDTO.getArchivos());

        String codigo = generarCodigo(tipoRequerimiento,contador);
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setCodigo(codigo);
        requerimiento.setDescripcion(requerimientoDTO.getDescripcion());
        requerimiento.setEstado(EstadoEnum.ABIERTO);
        requerimiento.setTipoRequerimiento(tipoRequerimiento);
        requerimiento.setEmisor(emisor);
        requerimiento.setUsuarioPropietario(propietario);
        requerimiento.setPrioridad(requerimientoDTO.getPrioridad());
        requerimiento.setAsunto(requerimientoDTO.getAsunto());
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

        return null;

    }

    private String generarCodigo(TipoRequerimiento tipoRequerimiento, long contador) {
        LocalDate fecha = LocalDate.now();
        int anio = fecha.getYear();
        contador++;
        String codigoContador = String.format("%011d", contador);
        return tipoRequerimiento.getCodigo() +"-" + anio + "-" + codigoContador;

    }


    @Override
    public List<Requerimiento> getRequerimientosById(Integer id) {
        List<Requerimiento> requerimientos = new ArrayList<>();
        Requerimiento requerimiento = requerimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Requerimiento no encontrado"));
        requerimientos.add(requerimiento);
        return requerimientos;
    }


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
    public List<RequerimientoDTO> convertirARequerimientoDTO(List<Requerimiento> requerimientos) {
        return requerimientos.stream()
                .map(requerimiento1 -> modelMapper.map(requerimiento1,RequerimientoDTO.class))
                .toList();
    }

    @Override
    public List<Requerimiento> getRequerimientoByFiltros(String tipo, String categoria, String estado) {
        //El CriteriaBuilder se utiliza para crear los componentes de la consulta
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //Se crea la consulta, en este caso una consulta para obtener objetos de la clase requerimiento
        CriteriaQuery<Requerimiento> cq = cb.createQuery(Requerimiento.class);
        //El root es el objeto principal de la consulta, en este caso el Requerimiento
        Root<Requerimiento> root= cq.from(Requerimiento.class);

        //Un predicado es una condicion que sera evaluada en la consulta, si un parametro es null, no se agrega a la consulta y se añade un predicado para filtrar ese campo
        List<Predicate> predicates = new ArrayList<>();
        if (tipo != null){
            predicates.add(cb.equal(root.get("tipo"),tipo));
        }
        if (categoria != null){
            predicates.add(cb.equal(root.get("categoria"),categoria));
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
