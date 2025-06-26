package com.inventario.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//SWAGGER URL: http://localhost:8080/doc/swagger-ui/index.html
//COMPARTIR DE FORMA LOCAL (MISMA RED, DIFERENTE DISPOSITIVO): http://MIIP:8080/api/v1/productos
//PARA OBTENER LA IP (WINDOWS): ipconfig EN CMD, ES LA IPv4
@SpringBootApplication
public class InventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	}
}
