package com.inventario.inventario.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.inventario.inventario.model.Producto;
import com.inventario.inventario.controller.ProductoControllerV2;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).getProductoById(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos"));
    }
}