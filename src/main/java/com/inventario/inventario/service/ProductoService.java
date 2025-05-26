package com.inventario.inventario.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventario.inventario.model.Producto;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(long id){
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Long id){
        productoRepository.deleteById(id);
    }
    
    //MAS DE UN PRODUCTO:
    public List<Producto> guardarProductos(List<Producto> productos){
        return productoRepository.saveAll(productos);
    }

    //PRODUCTOS DE UN PROVEEDOR EN ESPECIFICO:
    public List<Producto> findByProveedor(Proveedor proveedorBuscar) {
        return this.productoRepository.findByProveedor(proveedorBuscar);
    }

    //NUEVOS:

    //BUSCAR PRODUCTO POR NOMBRE:
    public List<Producto> findByNombreContaining(String texto) {
        return this.productoRepository.findByNombreContainingIgnoreCase(texto);
    }
    
    //PRODUCTOS VENCIDOS:
    public List<Producto> findProductosVencidos() {
        LocalDate hoy = LocalDate.now();
        return this.productoRepository.findByFechaVencimientoLessThanEqual(hoy);
    }
    
    //PRODUCTOS PROXIMOS A VENCER:
    public List<Producto> findProductosProximosVencer(int dias) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(dias);
        return this.productoRepository.findByFechaVencimientoBetween(hoy, fechaLimite);
    }
}
