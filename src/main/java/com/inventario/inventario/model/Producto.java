package com.inventario.inventario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity//Marca esta clase como una entidad JPA
@Table(name = "PRODUCTO")//Especifica el nombre de la tabla en la base de datos
@Data//Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor//Genera un constructor sin argumentos
@AllArgsConstructor//Genera un constructor con un argumento por cada campo en la clase
public class Producto {

    @Id//Identificador primario
    @GeneratedValue(strategy = GenerationType.IDENTITY)//El valor del ID se generará automáticamente
    private Integer id;//ID DEL PRODUCTO

    @Column(nullable=false)
    private String nombre;//NOMBRE DEL PRODUCTO

    @Column(nullable=false)
    private String descripcion;//DESCRIPCION DEL PRODUCTO

    @Column(nullable=true)//NO TODOS LOS PRODUCTOS VENCEN  
    private Date fechaVencimiento;//FECHA DE VENCIMIENTO DEL PRODUCTO

    @Column(nullable=false)
    private String categoria;//CATEGORIA DEL PRODUCTO

    @Column(nullable=false)
    private int cantidad;//CANTIDAD DEL PRODUCTO

    //(nullable=false) -> LA COLUMNA NO PUEDE SER NULA

    @ManyToOne
    @JoinColumn(name = "codigo_proveedor", referencedColumnName = "codigoProveedor", nullable = false)
    private Proveedor proveedor;
}   
