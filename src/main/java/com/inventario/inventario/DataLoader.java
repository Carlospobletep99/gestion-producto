package com.inventario.inventario;

import net.datafaker.Faker;

import java.util.Random;
import java.util.List;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.inventario.inventario.model.*;
import com.inventario.inventario.repository.*;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        
        //GENERAR PROVEEDORES:
        for (int i = 100; i < 200; i++) {
            Proveedor proveedor = new Proveedor();
            proveedor.setCodigoProveedor(i + 1);
            proveedor.setNombreProveedor(faker.company().name());
            proveedorRepository.save(proveedor);
        }
        List<Proveedor> proveedores = proveedorRepository.findAll();
        
        //GENERAR PRODUCTOS:
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence());
            producto.setFechaVencimiento(LocalDate.now().plusDays(faker.number().numberBetween(1, 365)));
            producto.setCategoria(faker.commerce().department());
            producto.setCantidad(faker.number().numberBetween(1, 100));
            producto.setProveedor(proveedores.get(random.nextInt(proveedores.size())));
            productoRepository.save(producto);
        }
    }
}