package com.inventario.inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.service.ProveedorService;

@RestController
// http://localhost:8080
@RequestMapping("/api/v1/proveedores")// PARA AGREGAR MAS DE UN PROVEEDOR: /bulk
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    //METODO PARA RETORNAR TODOS LOS PROVEEDORES:
    @GetMapping
    public ResponseEntity<List<Proveedor>> listar(){
        List<Proveedor> proveedores = proveedorService.findAll();
        if (proveedores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }

    //METODO PARA CREAR UN PROVEEDOR:
    @PostMapping
    public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor){
        Proveedor proveedorNuevo = proveedorService.save(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorNuevo); 
    }

    //METODO PARA RETORNAR UN PROVEEDOR POR SU CODIGO:
    @GetMapping("/{codigoProveedor}")
    public ResponseEntity<Proveedor> buscar(@PathVariable Integer codigoProveedor){
        try {
            Proveedor proveedor = proveedorService.findByCodigoProveedor(codigoProveedor);
            return ResponseEntity.ok(proveedor);
        } catch ( Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    //METODO PARA EDITAR DETALLES DE UN PROVEEDOR:
    @PutMapping("/{codigoProveedor}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Integer codigoProveedor, @RequestBody Proveedor proveedor){
        try{
            Proveedor prov = proveedorService.findByCodigoProveedor(codigoProveedor);
            prov.setCodigoProveedor(codigoProveedor);
            prov.setNombreProveedor(proveedor.getNombreProveedor());

            proveedorService.save(prov);
            return ResponseEntity.ok(prov);
        } catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    //METODO PARA BORRAR UN PROVEEDOR:
    @DeleteMapping("/{codigoProveedor}")
    public ResponseEntity<?> eliminar(@PathVariable Integer codigoProveedor){
        try{
            proveedorService.delete(codigoProveedor);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    //METODO PARA AGREGAR MAS DE UN PROVEEDOR:
    @PostMapping("/bulk")
    public ResponseEntity<List<Proveedor>> guardarProveedores(@RequestBody List<Proveedor> proveedores){
        List<Proveedor> guardados = proveedorService.guardarProveedores(proveedores);
        return ResponseEntity.ok(guardados);
    }

    //METODO PARA BUSCAR PROVEEDORES POR NOMBRE:
    @GetMapping("/buscar/{texto}")
    public ResponseEntity<List<Proveedor>> buscarPorNombre(@PathVariable String texto) {
        List<Proveedor> proveedores = proveedorService.findByNombreContaining(texto);
        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }
}
