package com.inventario.inventario.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventario.inventario.model.Producto;
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

    public void delete(long id){
        productoRepository.deleteById(id);
    }
    
    //MAS DE UN PRODUCTO:
    public List<Producto> guardarProductos(List<Producto> productos){
        return productoRepository.saveAll(productos);

    }
}
