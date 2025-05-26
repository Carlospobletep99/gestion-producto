package com.inventario.inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROVEEDOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @Column(columnDefinition = "NUMBER(3)")
    private Integer codigoProveedor;           

    @Column(nullable=false)
    private String nombreProveedor;
}