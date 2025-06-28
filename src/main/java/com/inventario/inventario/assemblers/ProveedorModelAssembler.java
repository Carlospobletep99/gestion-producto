package com.inventario.inventario.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.controller.ProveedorControllerV2;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<Proveedor, EntityModel<Proveedor>> {

    @Override
    public EntityModel<Proveedor> toModel(Proveedor proveedor) {
        return EntityModel.of(proveedor,
                linkTo(methodOn(ProveedorControllerV2.class).getProveedorByCodigoProveedor(proveedor.getCodigoProveedor())).withSelfRel(),
                linkTo(methodOn(ProveedorControllerV2.class).getAllProveedores()).withRel("proveedores"));
    }
}