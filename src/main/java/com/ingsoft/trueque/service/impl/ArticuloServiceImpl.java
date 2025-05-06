package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.ArticuloFiltroRequest;
import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.exception.ArticuloNotFoundException;
import com.ingsoft.trueque.exception.CategoriaNotFoundException;
import com.ingsoft.trueque.exception.ImagenNoValidaException;
import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.mapper.ArticuloMapper;
import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.Categoria;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.repository.ArticuloRepository;
import com.ingsoft.trueque.repository.CategoriaRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.ArticuloService;
import com.ingsoft.trueque.specification.ArticuloSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

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

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetArticulo> getAllArticulosDisponibles(ArticuloFiltroRequest filtroRequest,
                                                        Pageable pageable) {

        Specification<Articulo> spec = Specification
                .where(ArticuloSpecification.conCategoria(filtroRequest.categoria()))
                .and(ArticuloSpecification.conEstado(filtroRequest.estado()))
                .and(ArticuloSpecification.conNombre(filtroRequest.nombre()));

        return articuloRepository.findAllByEstado(spec, pageable, EstadoArticulo.DISPONIBLE).map(articuloMapper::toGetArticulo);
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
        //Categoria categoria = categoriaRepository.getCategoriaById(articulo.idCategoria())
        Categoria categoria = categoriaRepository.getCategoriaById(1L)
                .orElseThrow(() -> new CategoriaNotFoundException("Error al guardar articulo con id categoria "+ articulo.getIdCategoria()+", no encontrada en BD"));

        //Usuario propietario = usuarioRepository.getUsuarioById(articulo.idPropietario())
        Usuario propietario = usuarioRepository.getUsuarioById(1L)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al guardar el articulo, usuario con id "+articulo.getIdPropietario()+", no encontrado en BD"));

        Articulo articuloToSave = articuloMapper.toArticulo(articulo);
        articuloToSave.setCategoria(categoria);
        articuloToSave.setEstado(EstadoArticulo.DISPONIBLE);
        articuloToSave.setPropietario(propietario);

        if(imagen !=null){
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
        if(articuloRepository.existsById(id))
            articuloRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetArticulo eliminadoLogico(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Error al eliminar el articulo con id "+id+", no encontrado en BD"));
        articulo.setEstado(EstadoArticulo.DESACTIVADO);
        return articuloMapper.toGetArticulo(articuloRepository.save(articulo));
    }
}
