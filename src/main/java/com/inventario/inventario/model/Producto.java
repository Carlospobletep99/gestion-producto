package com.inventario.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity//Marca esta clase como una entidad JPA
@Table(name = "PRODUCTO")//Especifica el nombre de la tabla en la base de datos
@Data//Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor//Genera un constructor sin argumentos
@AllArgsConstructor//Genera un constructor con un argumento por cada campo en la clase
public class Producto {

    @Id//Identificador primario
    @GeneratedValue(strategy = GenerationType.IDENTITY)//El valor del ID se generará automáticamente
    @Schema(description = "Codigo verificador del producto", example = "1")
    private Integer id;//ID DEL PRODUCTO

    @Column(nullable=false)
    @Schema(description = "Nombre del producto", example = "Detergente Ecológico")
    private String nombre;//NOMBRE DEL PRODUCTO

    @Column(nullable=false)
    @Schema(description = "Descripcion del producto", example = "Biodegradable para ropa, 2L")
    private String descripcion;//DESCRIPCION DEL PRODUCTO

    @Column(nullable=true)//NO TODOS LOS PRODUCTOS VENCEN  
    @Schema(description = "Fecha de vencimiento del prducto", example = "2026-01-05")
    private LocalDate fechaVencimiento;//FECHA DE VENCIMIENTO DEL PRODUCTO

    @Column(nullable=false)
    @Schema(description = "Categoria a la que pertenece el prducto", example = "Limpieza")
    private String categoria;//CATEGORIA DEL PRODUCTO

    @Column(nullable=false)
    @Schema(description = "Cantidad por unidad del prducto", example = "50")
    private int cantidad;//CANTIDAD DEL PRODUCTO

    //(nullable=false) -> LA COLUMNA NO PUEDE SER NULA

    @ManyToOne
    @JoinColumn(name = "codigo_proveedor", referencedColumnName = "codigoProveedor", nullable = false)
    private Proveedor proveedor;
}   
