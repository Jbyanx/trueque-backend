package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.ArticuloFiltroRequest;
import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.exception.*;
import com.ingsoft.trueque.mapper.ArticuloMapper;
import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.Categoria;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.model.util.Rol;
import com.ingsoft.trueque.repository.ArticuloRepository;
import com.ingsoft.trueque.repository.CategoriaRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.ArticuloService;
import com.ingsoft.trueque.specification.ArticuloSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticuloServiceImpl implements ArticuloService {
    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FileStorageService fileStorageService;

    public ArticuloServiceImpl(ArticuloRepository articuloRepository, ArticuloMapper articuloMapper, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository, FileStorageService fileStorageService) {
        this.articuloRepository = articuloRepository;
        this.articuloMapper = articuloMapper;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Page<GetArticulo> getAllArticulosDisponibles(ArticuloFiltroRequest filtroRequest,
                                                        Pageable pageable) {

        Persona principal = obtenerPrincipal();
        Long idPrincipal = (principal != null) ? principal.getId() : null;

        Specification<Articulo> spec = Specification
                .where(ArticuloSpecification.conCategoria(filtroRequest.idCategoria()))
                .and(ArticuloSpecification.conEstado(EstadoArticulo.DISPONIBLE))
                .and(ArticuloSpecification.conNombre(filtroRequest.nombre()));

        // Solo excluye si usuario autenticado
        if (idPrincipal != null) {
            spec = spec.and(ArticuloSpecification.sinPropietario(true, idPrincipal));
        }

        return articuloRepository.findAllByEstado(spec, pageable).map(articuloMapper::toGetArticulo);
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetArticulo getArticuloById(Long id) {
        return articuloRepository.findById(id)
                .map(articuloMapper::toGetArticulo)
                .orElseThrow(() -> new ArticuloNotFoundException("Error al buscar el Articulo con id "+id+", no encontrado en BD"));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetArticulo saveArticulo(SaveArticulo articulo, MultipartFile imagen) {
        Categoria categoria = categoriaRepository.getCategoriaById(articulo.getIdCategoria())
                .orElseThrow(() -> new CategoriaNotFoundException("Error al guardar articulo con id categoria "+ articulo.getIdCategoria()+", no encontrada en BD"));

        Persona propietario = obtenerPrincipal();

        Articulo articuloToSave = articuloMapper.toArticulo(articulo);
        articuloToSave.setCategoria(categoria);
        articuloToSave.setEstado(EstadoArticulo.DISPONIBLE);
        articuloToSave.setPropietario((Usuario) propietario);

        if(imagen != null){
            try{
                if (imagen != null && !imagen.isEmpty()) {
                    String rutaImagen = fileStorageService.guardarImagen(imagen);
                    articuloToSave.setRutaImagen(rutaImagen);
                }
            } catch (Exception e) {
                throw new ImagenNoValidaException("Error al guardar la imagen");
            }
        }

        return articuloMapper.toGetArticulo(articuloRepository.save(articuloToSave));
    }

    /***
     * Cambiar nombre, descripcion e imagen del articulo
     */
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    @Transactional
    public GetArticulo updateArticuloById(Long id, SaveArticulo articulo, MultipartFile file) {
        Articulo articuloBd = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Error al buscar el Articulo con id "+id+", no encontrado en BD"));

        Persona actual = obtenerPrincipal();

        boolean esPropietario = actual.getId().equals(articuloBd.getPropietario().getId());
        boolean esAdmin = actual.getRol().equals(Rol.ADMINISTRADOR);

        if(!esPropietario && !esAdmin){
            throw new AccesoNoPermitidoException("No tienes permiso para actualizar este articulo");
        }

        updateArticulo(articuloBd, articulo, file);
        return articuloMapper.toGetArticulo(articuloRepository.save(articuloBd));
    }

    private void updateArticulo(Articulo articuloBd, SaveArticulo articulo, MultipartFile file) {
        if(StringUtils.hasText(articulo.getNombre())){
            articuloBd.setNombre(articulo.getNombre());
        }
        if(StringUtils.hasText(articulo.getDescripcion())){
            articuloBd.setDescripcion(articulo.getDescripcion());
        }
        try {
            if (file != null && !file.isEmpty()) {
                String rutaImagen = fileStorageService.guardarImagen(file);
                articuloBd.setRutaImagen(rutaImagen);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public void deleteArticuloById(Long id) {
        if(articuloRepository.existsById(id)){
            articuloRepository.deleteById(id);
        }else{
            throw new ArticuloNotFoundException("El articulo no se puede eliminar porque no existe en BD ID:"+id);
        }
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetArticulo eliminadoLogico(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Error al eliminar el artículo con id " + id + ", no encontrado en BD"));

        Persona actual = obtenerPrincipal();

        boolean esPropietario = actual.getId().equals(articulo.getPropietario().getId());
        boolean esAdmin = actual.getRol().equals(Rol.ADMINISTRADOR);

        if (!esPropietario && !esAdmin) {
            throw new RuntimeException("No tienes permisos para eliminar este artículo");
        }

        articulo.setEstado(EstadoArticulo.DESACTIVADO);
        return articuloMapper.toGetArticulo(articuloRepository.save(articulo));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetArticulo> obtenerMisArticulos(Pageable pageable) {
        Persona actual = obtenerPrincipal();

        List<GetArticulo> articulosFiltrados = articuloRepository.findAll(pageable).stream()
                .filter(a -> a.getPropietario().getId().equals(actual.getId()))
                .map(articuloMapper::toGetArticulo)
                .collect(Collectors.toList());

        return new PageImpl<>(articulosFiltrados, pageable, articulosFiltrados.size());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetArticulo> getAllArticulosDisponiblesByUsuarioId(Pageable pageable, Long id) {
        Usuario usuario = usuarioRepository.getUsuarioById(id)
                        .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado en BD"));

        return articuloRepository.getArticulosDisponiblesByUsuarioId(id, pageable)
                .map(a -> articuloMapper.toGetArticulo(a));
    }

    @Override
    public Page<GetArticulo> getArticulosByIdCategoria(Long idCategoria, Pageable pageable) {
        if(!categoriaRepository.existsById(idCategoria)){
            throw new CategoriaNotFoundException("Categoria no encontrada en B");
        }
        return articuloRepository.getArticulosByCategoriaId(idCategoria, pageable)
                .map(a -> articuloMapper.toGetArticulo(a));
    }


    public Persona obtenerPrincipal() {
        try {
            return (Persona) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            // No hay usuario autenticado o no se puede obtener el principal
            return null;
        }
    }
}
