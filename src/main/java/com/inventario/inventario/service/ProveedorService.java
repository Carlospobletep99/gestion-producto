package com.inventario.inventario.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.repository.ProveedorRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> findAll(){
        return proveedorRepository.findAll();
    }

    public Proveedor findByCodigoProveedor(Integer codigoProveedor){
        return proveedorRepository.findById(codigoProveedor).get();
    }

    public Proveedor save(Proveedor proveedor){
        return proveedorRepository.save(proveedor);
    }

    public void delete(Integer codigoProveedor){
        proveedorRepository.deleteById(codigoProveedor);
    }
    
    //MAS DE UN PROVEEDOR:
    public List<Proveedor> guardarProveedores(List<Proveedor> proveedores){
        return proveedorRepository.saveAll(proveedores);
    }

    //BUSCAR PROVEEDOR POR NOMBRE:
    public List<Proveedor> findByNombreContaining(String texto) {
        return this.proveedorRepository.findByNombreProveedorContainingIgnoreCase(texto);
    }
}
