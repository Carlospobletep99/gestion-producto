package com.inventario.inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inventario.inventario.model.Producto;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.service.ProductoService;

@RestController
// http://localhost:8080
@RequestMapping("/api/v1/productos")// PARA AGREGAR MAS DE UN PRODUCTO: /bulk
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //METODO PARA RETORNAR TODOS LOS PRODUCTOS:
    @GetMapping
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    //METODO PARA CREAR UN PRODUCTO:
    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto productoNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo); 
    }

    //METODO PARA RETORNAR UN PRODUCTO POR SU ID:
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Integer id){
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch ( Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    //METODO PARA EDITAR DETALLES DE UN PRODUCTO:
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto){
        try{
            Producto prod = productoService.findById(id);
            prod.setId(id);
            prod.setNombre(producto.getNombre());
            prod.setDescripcion(producto.getDescripcion());
            prod.setFechaVencimiento(producto.getFechaVencimiento());
            prod.setCategoria(producto.getCategoria());
            prod.setCantidad(producto.getCantidad());

            productoService.save(prod);
            return ResponseEntity.ok(producto);
        } catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    //METODO PARA BORRAR UN PRODUCTO:
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    //METODO PARA AGREGAR MAS DE UN PRODUCTO:
    @PostMapping("/bulk")
    public ResponseEntity<List<Producto>> guardarProductos(@RequestBody List<Producto> productos){
        List<Producto> guardados = productoService.guardarProductos(productos);
        return ResponseEntity.ok(guardados);
    }

    //METODO PARA RETORNAR PRODUCTOS PERTENECIENTES A UN PROVEEDOR ESPECIFICO:
    @GetMapping("/proveedor/{codigoProveedor}")
    public ResponseEntity<List<Producto>> getStoresByProveedor(@PathVariable Integer codigoProveedor) {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigoProveedor(codigoProveedor);
        List<Producto> listaProductospv = this.productoService.findByProveedor(proveedor);
        if (listaProductospv.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaProductospv);
    }
}


