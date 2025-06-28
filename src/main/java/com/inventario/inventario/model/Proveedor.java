package com.inventario.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "PROVEEDOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @Column(columnDefinition = "INT(3)")
    @Schema(description = "Codigo identificador del proveedor", example = "101")
    private Integer codigoProveedor;           

    @Column(nullable=false)
    @Schema(description = "Nombre del proveedor", example = "Verde Hogar")
    private String nombreProveedor;
}