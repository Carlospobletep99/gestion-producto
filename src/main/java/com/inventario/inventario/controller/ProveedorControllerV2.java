package com.inventario.inventario.controller;

import com.inventario.inventario.assemblers.ProveedorModelAssembler;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.service.ProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// http://localhost:8080/api/v2/proveedores
// http://localhost:8080/api/v2/proveedores/bulk (para agregar más de un proveedor)
@RestController
@RequestMapping("/api/v2/proveedores")
public class ProveedorControllerV2 {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Proveedor>> getAllProveedores() {
        List<EntityModel<Proveedor>> proveedores = proveedorService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(proveedores,
                linkTo(methodOn(ProveedorControllerV2.class).getAllProveedores()).withSelfRel());
    }

    @GetMapping(value = "/{codigoProveedor}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Proveedor> getProveedorByCodigoProveedor(@PathVariable Integer codigoProveedor) {
        Proveedor proveedor = proveedorService.findByCodigoProveedor(codigoProveedor);
        return assembler.toModel(proveedor);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Proveedor>> createProveedor(@RequestBody Proveedor proveedor) {
        Proveedor newProveedor = proveedorService.save(proveedor);
        return ResponseEntity
                .created(linkTo(methodOn(ProveedorControllerV2.class).getProveedorByCodigoProveedor(newProveedor.getCodigoProveedor())).toUri())
                .body(assembler.toModel(newProveedor));
    }

    @PutMapping(value = "/{codigoProveedor}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Proveedor>> updateProveedor(@PathVariable Integer codigoProveedor, @RequestBody Proveedor proveedor) {
        proveedor.setCodigoProveedor(codigoProveedor);
        Proveedor updatedProveedor = proveedorService.save(proveedor);
        return ResponseEntity
                .ok(assembler.toModel(updatedProveedor));
    }

    @DeleteMapping(value = "/{codigoProveedor}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteProveedor(@PathVariable Integer codigoProveedor) {
        proveedorService.delete(codigoProveedor);
        return ResponseEntity.noContent().build();
    }
}