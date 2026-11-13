package com.fredy.spring.actividad4.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fredy.spring.actividad4.dto.ProductoRequestDTO;
import com.fredy.spring.actividad4.dto.ProductoResponseDTO;
import com.fredy.spring.actividad4.entity.Categoria;
import com.fredy.spring.actividad4.entity.Producto;
import com.fredy.spring.actividad4.repository.CategoriaRepository;
import com.fredy.spring.actividad4.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    private ProductoResponseDTO mapToDTO(Producto p) {
        return new ProductoResponseDTO(
            p.getId(),
            p.getNombre(),
            p.getPrecio(),
            p.getStock(),
            p.getCategoria().getId(),
            p.getCategoria().getNombre()
        );
    }

    public Page<ProductoResponseDTO> listarPaginado(Pageable pageable) {
        return productoRepository.findAll(pageable).map(this::mapToDTO);
    }

    public Optional<ProductoResponseDTO> buscarPorId(Long id) {
        return productoRepository.findById(id).map(this::mapToDTO);
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Categoria cat = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.categoriaId()));

        Producto producto = new Producto(dto.nombre(), dto.precio(), dto.stock(), cat);
        return mapToDTO(productoRepository.save(producto));
    }

    public Optional<ProductoResponseDTO> actualizar(Long id, ProductoRequestDTO dto) {
        return productoRepository.findById(id).map(producto -> {
            Categoria cat = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.categoriaId()));

            producto.setNombre(dto.nombre());
            producto.setPrecio(dto.precio());
            producto.setStock(dto.stock());
            producto.setCategoria(cat);
            return mapToDTO(productoRepository.save(producto));
        });
    }

    public boolean eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }
}