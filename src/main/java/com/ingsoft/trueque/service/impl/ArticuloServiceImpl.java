package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.exception.ArticuloNotFoundException;
import com.ingsoft.trueque.exception.CategoriaNotFoundException;
import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.mapper.ArticuloMapper;
import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.Categoria;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.repository.ArticuloRepository;
import com.ingsoft.trueque.service.ArticuloService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ArticuloServiceImpl implements ArticuloService {
    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ArticuloServiceImpl(ArticuloRepository articuloRepository, ArticuloMapper articuloMapper, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.articuloRepository = articuloRepository;
        this.articuloMapper = articuloMapper;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<GetArticulo> getAllArticulos(Pageable pageable) {
        return articuloRepository.findAll(pageable).map(articuloMapper::toGetArticulo);
    }

    @Override
    public GetArticulo getArticuloById(Long id) {
        return articuloRepository.findById(id)
                .map(articuloMapper::toGetArticulo)
                .orElseThrow(() -> new ArticuloNotFoundException("Error al buscar el Articulo con id "+id+", no encontrado en BD"));
    }

    @Override
    public GetArticulo saveArticulo(SaveArticulo articulo) {
        Categoria categoria = categoriaRepository.getCategoriaById(articulo.idCategoria())
                .orElseThrow(() -> new CategoriaNotFoundException("Error al guardar articulo con id categoria "+ articulo.idCategoria()+", no encontrada en BD"));

        Usuario propietario = usuarioRepository.getUsuarioById(articulo.idPropietario())
                .orElseThrow(() -> new UsuarioNotFoundException("Error al guardar el articulo, usuario con id "+articulo.idPropietario()+", no encontrado en BD"));

        Articulo articuloToSave = articuloMapper.toArticulo(articulo);
        articuloToSave.setCategoria(categoria);
        articuloToSave.setPropietario(propietario);

        return articuloMapper.toGetArticulo(articuloRepository.save(articuloToSave));
    }

    /***
     * Cambiar nombre, descripcion e imagen del articulo
     * @param id
     * @param articulo
     * @return GetArticulo
     */
    @Override
    @Transactional
    public GetArticulo updateArticuloById(Long id, SaveArticulo articulo) {
        Articulo articuloBd = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Error al buscar el Articulo con id "+id+", no encontrado en BD"));
        updateArticulo(articuloBd, articulo);
        return articuloMapper.toGetArticulo(articuloRepository.save(articuloBd));
    }

    private void updateArticulo(Articulo articuloBd, SaveArticulo articulo) {
        if(StringUtils.hasText(articulo.nombre())){
            articuloBd.setNombre(articulo.nombre());
        }
        if(StringUtils.hasText(articulo.descripcion())){
            articuloBd.setDescripcion(articulo.descripcion());
        }
        if(StringUtils.hasText(articulo.rutaImagen())){
            articuloBd.setRutaImagen(articulo.rutaImagen());
        }
    }

    @Override
    public void deleteArticuloById(Long id) {
        if(articuloRepository.existsById(id))
            articuloRepository.deleteById(id);
    }
}
