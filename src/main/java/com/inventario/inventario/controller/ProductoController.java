package com.inventario.inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventario.inventario.model.Producto;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// http://localhost:8080/api/v1/productos
// http://localhost:8080/api/v1/productos/bulk (para agregar más de un producto)
@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos del inventario")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    //METODO PARA RETORNAR TODOS LOS PRODUCTOS:
    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Obtiene lista de todos los productos existentes en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados con exito en el inventario",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "Productos no encontrados en el inventario")
    })
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    //METODO PARA CREAR UN PRODUCTO:
    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado con exito en el inventario",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "No se pudo crear el producto, datos incorrectos")
    })
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        try {
            Producto productoNuevo = productoService.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    //METODO PARA RETORNAR UN PRODUCTO POR SU ID:
    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto por su codigo identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado con exito en el inventario",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado en el inventario")
    })
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
    @Operation(summary = "Actualizar datos de un producto", description = "Actualiza un producto existente en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente en el inventario",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado en el inventario"),
        @ApiResponse(responseCode = "400", description = "No se pudo actualizar el producto, datos erroneos")
    })
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
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del inventario por su codigo identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado del inventario con exito"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado en el inventario")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        try{
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    //METODO PARA AGREGAR MAS DE UN PRODUCTO:
    @PostMapping("/bulk")
    @Operation(summary = "Crear varios productos", description = "Crea varios productos a la vez en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Productos creados en el inventario con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "400", description = "No se pudieron crear los productos, datos incorrectos")
    })
    public ResponseEntity<List<Producto>> guardarProductos(@RequestBody List<Producto> productos){
        try {
            List<Producto> guardados = productoService.guardarProductos(productos);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //METODO PARA RETORNAR PRODUCTOS PERTENECIENTES A UN PROVEEDOR ESPECIFICO:
    @GetMapping("/proveedor/{codigoProveedor}")
    @Operation(summary = "Obtener productos por proveedor", description = "Obtiene lista de productos pertenecientes a un proveedor en específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No existen productos en el proveedor especificado")
    })
    public ResponseEntity<List<Producto>> getStoresByProveedor(@PathVariable Integer codigoProveedor) {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigoProveedor(codigoProveedor);
        List<Producto> listaProductospv = this.productoService.findByProveedor(proveedor);
        if (listaProductospv.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaProductospv);
    }

    //NUEVOS:

    //METODO PARA BUSCAR PRODUCTOS POR NOMBRE:
    @GetMapping("/buscar/{texto}")
    @Operation(summary = "Obtener productos por nombre", description = "Obtiene productos por texto completo o parcial de estos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos encontrados con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "Productos no encontrados, no existen productos con el nombre ingresado")
    })
    public ResponseEntity<List<Producto>> buscarPorNombre(@PathVariable String texto) {
        List<Producto> productos = productoService.findByNombreContaining(texto);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    //METODO PARA RETORNAR PRODUCTOS VENCIDOS:
    @GetMapping("/vencidos")
    @Operation(summary = "Obtener productos vencidos", description = "Obtiene la lista de productos cuya fecha de vencimiento ya ha pasado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos vencidos obtenidos con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No existen productos vencidos en el inventario")
    })
    public ResponseEntity<List<Producto>> productosVencidos() {
        List<Producto> productos = productoService.findProductosVencidos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    //METODO PARA RETORNAR PRODUCTOS PROXIMOS A VENCER:
    @GetMapping("/proximos-vencer/{dias}")
    @Operation(summary = "Obtener productos próximos a vencer", description = "Obtiene los productos cuya fecha de vencimiento es dentro de los próximos 'n' días")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos próximos a vencer obtenidos con éxito",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "204", description = "No existen productos próximos a vencer en el rango indicado"),
        @ApiResponse(responseCode = "400", description = "Número inválido, los días deben ser un número positivo")
    })
    public ResponseEntity<List<Producto>> productosProximosVencer(@PathVariable int dias) {
        if (dias <= 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Producto> productos = productoService.findProductosProximosVencer(dias);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }
}


