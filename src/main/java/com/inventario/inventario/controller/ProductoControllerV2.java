package com.inventario.inventario.controller;

import com.inventario.inventario.assemblers.ProductoModelAssembler;
import com.inventario.inventario.model.Producto;
import com.inventario.inventario.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// http://localhost:8080/api/v2/productos
// http://localhost:8080/api/v2/productos/bulk (para agregar más de un producto)
@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<EntityModel<Producto>> productos = productoService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> getProductoById(@PathVariable Integer id) {
        Producto producto = productoService.findById(id);
        return assembler.toModel(producto);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getId())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity
                .ok(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteProducto(@PathVariable Integer id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}