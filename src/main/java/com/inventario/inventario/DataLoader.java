package com.inventario.inventario;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.inventario.inventario.model.Producto;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.repository.ProductoRepository;
import com.inventario.inventario.repository.ProveedorRepository;

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

        //CREAR PROVEEDORES
        for (int i = 0; i < 10; i++) {
            Proveedor proveedor = new Proveedor();
            proveedor.setCodigoProveedor(i + 1);
            proveedor.setNombreProveedor(faker.company().name());
            proveedorRepository.save(proveedor);
        }

        //CREAR PRODUCTOS
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto();
            producto.setId(i + 1);
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().sentence());
            producto.setFechaVencimiento(faker.date().future(365, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            producto.setCategoria(faker.commerce().department());
            producto.setCantidad(faker.number().numberBetween(1, 100));
            producto.setProveedor(proveedorRepository.findById(faker.number().numberBetween(1, 10)).orElse(null));
            productoRepository.save(producto);
        }
    }
}