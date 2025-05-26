package com.inventario.inventario.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inventario.inventario.model.Producto;
import com.inventario.inventario.model.Proveedor;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    public List<Producto> findByProveedor(Proveedor proveedor);
    
    //NUEVOS:

    //BUSCAR PRODUCTO POR NOMBRE (MAYUS. Y/O MINUS.):
    public List<Producto> findByNombreContainingIgnoreCase(String texto);
    
    //PRODUCTOS VENCIDOS:
    public List<Producto> findByFechaVencimientoLessThanEqual(LocalDate fecha);
    
    //PRODUCTOS PROXIMOS A VENCER:
    public List<Producto> findByFechaVencimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);
}

