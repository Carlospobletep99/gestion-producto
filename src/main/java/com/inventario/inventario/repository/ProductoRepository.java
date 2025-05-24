package com.inventario.inventario.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inventario.inventario.model.Producto;
import com.inventario.inventario.model.Proveedor;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    public List<Producto> findByProveedor(Proveedor proveedor);
}
