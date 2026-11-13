package com.fredy.spring.actividad4.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fredy.spring.actividad4.entity.Categoria;
import com.fredy.spring.actividad4.entity.Producto;
import com.fredy.spring.actividad4.repository.CategoriaRepository;
import com.fredy.spring.actividad4.repository.ProductoRepository;

//dos productos de prueba, uno de papeleria y otro de tecnologia que se cargan al iniciar la aplicacion si no hay categorias en la base de datos
@Component
public class DataLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public DataLoader(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() == 0) {
            Categoria papeleria = categoriaRepository.save(new Categoria("Papeleria", "Articulos de oficina y escolares"));
            Categoria tecnologia = categoriaRepository.save(new Categoria("Tecnologia", "Dispositivos electronicos y accesorios"));

            productoRepository.save(new Producto("Cuaderno Profesional", 38.00, 50, papeleria));
            productoRepository.save(new Producto("Memoria USB 64GB", 160.00, 20, tecnologia));
        }
    }
}