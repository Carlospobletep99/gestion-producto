package com.inventario.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// http://localhost:8080/api/v1/proveedores
// http://localhost:8080/api/v1/proveedores/bulk (para agregar más de un proveedor)
@RestController
@RequestMapping("/api/v1/proveedores")// PARA AGREGAR MAS DE UN PROVEEDOR: /bulk
@Tag(name = "Proveedores", description = "Operaciones relacionadas con los proveedores asociados a los productos")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    //METODO PARA RETORNAR TODOS LOS PROVEEDORES:
    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Obtiene la lista completa de proveedores registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedores encontrados con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "204", description = "No existen proveedores registrados")
    })
    public ResponseEntity<List<Proveedor>> listar(){
        List<Proveedor> proveedores = proveedorService.findAll();
        if (proveedores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }

    //METODO PARA CREAR UN PROVEEDOR:
    @PostMapping
    @Operation(summary = "Crear un proveedor", description = "Crea un nuevo proveedor en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proveedor creado con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "400", description = "Datos del proveedor inválidos")
    })
    public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor){
        Proveedor proveedorNuevo = proveedorService.save(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorNuevo); 
    }

    //METODO PARA RETORNAR UN PROVEEDOR POR SU CODIGO:
    @GetMapping("/{codigoProveedor}")
    @Operation(summary = "Buscar proveedor por código", description = "Obtiene un proveedor específico mediante su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
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
    @Operation(summary = "Actualizar proveedor", description = "Actualiza los datos de un proveedor existente mediante su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor actualizado con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para la actualización")
    })
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
    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor del sistema mediante su código")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Proveedor eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
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
    @Operation(summary = "Crear múltiples proveedores", description = "Crea varios proveedores a la vez en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedores creados con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "400", description = "Error al crear los proveedores, datos inválidos")
    })
    public ResponseEntity<List<Proveedor>> guardarProveedores(@RequestBody List<Proveedor> proveedores){
        List<Proveedor> guardados = proveedorService.guardarProveedores(proveedores);
        return ResponseEntity.ok(guardados);
    }

    //METODO PARA BUSCAR PROVEEDORES POR NOMBRE:
    @GetMapping("/buscar/{texto}")
    @Operation(summary = "Buscar proveedores por nombre", description = "Obtiene proveedores cuyo nombre contenga total o parcialmente el texto especificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedores encontrados con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "204", description = "No se encontraron proveedores con el nombre ingresado")
    })
    public ResponseEntity<List<Proveedor>> buscarPorNombre(@PathVariable String texto) {
        List<Proveedor> proveedores = proveedorService.findByNombreContaining(texto);
        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }
}
