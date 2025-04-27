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
import com.ingsoft.trueque.repository.CategoriaRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.ArticuloService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    public GetArticulo saveArticulo(SaveArticulo articulo, MultipartFile imagen) {
        //Categoria categoria = categoriaRepository.getCategoriaById(articulo.idCategoria())
        Categoria categoria = categoriaRepository.getCategoriaById(1L)
                .orElseThrow(() -> new CategoriaNotFoundException("Error al guardar articulo con id categoria "+ articulo.idCategoria()+", no encontrada en BD"));

        //Usuario propietario = usuarioRepository.getUsuarioById(articulo.idPropietario())
        Usuario propietario = usuarioRepository.getUsuarioById(1L)
                .orElseThrow(() -> new UsuarioNotFoundException("Error al guardar el articulo, usuario con id "+articulo.idPropietario()+", no encontrado en BD"));

        Articulo articuloToSave = articuloMapper.toArticulo(articulo);
        articuloToSave.setCategoria(categoria);
        articuloToSave.setPropietario(propietario);

        try{
            if (imagen != null && !imagen.isEmpty()) {
                String rutaImagen = fileStorageService.guardarImagen(imagen);
                articuloToSave.setRutaImagen(rutaImagen);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return articuloMapper.toGetArticulo(articuloRepository.save(articuloToSave));
    }

    /***
     * Cambiar nombre, descripcion e imagen del articulo
     */
    @Override
    @Transactional
    public GetArticulo updateArticuloById(Long id, SaveArticulo articulo, MultipartFile file) {
        Articulo articuloBd = articuloRepository.findById(id)
                .orElseThrow(() -> new ArticuloNotFoundException("Error al buscar el Articulo con id "+id+", no encontrado en BD"));
        updateArticulo(articuloBd, articulo, file);
        return articuloMapper.toGetArticulo(articuloRepository.save(articuloBd));
    }

    private void updateArticulo(Articulo articuloBd, SaveArticulo articulo, MultipartFile file) {
        if(StringUtils.hasText(articulo.nombre())){
            articuloBd.setNombre(articulo.nombre());
        }
        if(StringUtils.hasText(articulo.descripcion())){
            articuloBd.setDescripcion(articulo.descripcion());
        }
        if(StringUtils.hasText(articulo.rutaImagen())){
            articuloBd.setRutaImagen(articulo.rutaImagen());
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

    @Override
    public void deleteArticuloById(Long id) {
        if(articuloRepository.existsById(id))
            articuloRepository.deleteById(id);
    }
}
