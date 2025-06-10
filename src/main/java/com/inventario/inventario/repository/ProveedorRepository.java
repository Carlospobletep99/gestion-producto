package com.inventario.inventario.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inventario.inventario.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    
    //BUSCAR PROVEEDOR POR NOMBRE (MAYUS. Y/O MINUS.):
    public List<Proveedor> findByNombreProveedorContainingIgnoreCase(String texto);
}
