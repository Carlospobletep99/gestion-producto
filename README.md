# 🌱 Microservicio de Inventario - EcoMarket SPA

## 📋 Descripción del Proyecto

Este microservicio forma parte del sistema de gestión de inventario de **EcoMarket SPA**, una empresa dedicada a la venta de productos ecológicos. El sistema permite la gestión completa de productos y proveedores, con funcionalidades avanzadas como control de vencimientos, búsquedas inteligentes y APIs REST documentadas.

## 🎯 Características Principales

### 📦 Gestión de Productos
- ✅ CRUD completo de productos
- 🔍 Búsqueda por nombre (insensible a mayúsculas/minúsculas)
- 📅 Control de productos vencidos
- ⏰ Monitoreo de productos próximos a vencer
- 📊 Filtrado por proveedor
- 🔄 Operaciones en lote (bulk operations)

### 🏪 Gestión de Proveedores
- ✅ CRUD completo de proveedores
- 🔍 Búsqueda por nombre de proveedor
- 🔄 Operaciones en lote
- 📋 Listado de productos por proveedor

### 🌐 APIs REST
- 🔗 **API v1**: REST tradicional con documentación Swagger
- 🔗 **API v2**: REST con HATEOAS (Hypermedia as the Engine of Application State)
- 📚 Documentación interactiva con Swagger UI

### 🧪 Testing
- ✅ Pruebas unitarias con JUnit 5
- 🎭 Mocking con Mockito
- 📊 Cobertura de servicios principales

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17+ | Lenguaje principal |
| Spring Boot | 3.x | Framework principal |
| Spring Data JPA | 3.x | Persistencia de datos |
| Spring HATEOAS | 3.x | API REST nivel 3 |
| MySQL | 8.x | Base de datos |
| Swagger/OpenAPI | 3.x | Documentación API |
| Lombok | Latest | Reducción de código boilerplate |
| DataFaker | Latest | Generación de datos de prueba |
| JUnit 5 | Latest | Testing |
| Mockito | Latest | Mocking para pruebas |

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/inventario/inventario/
│   │   ├── assemblers/          # HATEOAS assemblers
│   │   ├── config/              # Configuraciones
│   │   ├── controller/          # Controladores REST
│   │   ├── model/               # Entidades JPA
│   │   ├── repository/          # Repositorios de datos
│   │   ├── service/             # Lógica de negocio
│   │   ├── DataLoader.java      # Carga de datos inicial
│   │   └── InventarioApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       ├── application-test.properties
│       └── application-aws.properties
└── test/
    └── java/com/inventario/inventario/
        ├── ProductoServiceTest.java
        └── ProveedorServiceTest.java
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- ☕ Java 17 o superior
- 🐬 MySQL 8.x
- 📦 Maven 3.6+

### 1. Clonar el repositorio
```bash
git clone [URL_DEL_REPOSITORIO]
cd inventario
```

### 2. Configurar Base de Datos

#### Perfil de Desarrollo (dev)
```sql
CREATE DATABASE inventariodb_dev;
```

#### Perfil de Testing (test)
```sql
CREATE DATABASE inventariodb_test;
```

#### Perfil de produccion (aws)
```sql
CREATE DATABASE inventariodb_aws;
```

### 3. Configurar application.properties
El proyecto incluye múltiples perfiles:
- `dev`: Desarrollo local
- `test`: Testing
- `aws`: Producción en AWS

### 4. Ejecutar la aplicación
```bash
# Desarrollo
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Testing
mvn spring-boot:run -Dspring-boot.run.profiles=test

# Producción
mvn spring-boot:run -Dspring-boot.run.profiles=aws
```

## 📚 Documentación API

### Swagger UI
Una vez iniciada la aplicación, accede a la documentación interactiva:
```
http://localhost:8080/doc/swagger-ui/index.html
```

### Endpoints Principales

#### API v1 (REST Tradicional)

**Productos**
- `GET /api/v1/productos` - Listar todos los productos
- `POST /api/v1/productos` - Crear producto
- `GET /api/v1/productos/{id}` - Obtener producto por ID
- `PUT /api/v1/productos/{id}` - Actualizar producto
- `DELETE /api/v1/productos/{id}` - Eliminar producto
- `POST /api/v1/productos/bulk` - Crear múltiples productos
- `GET /api/v1/productos/proveedor/{codigoProveedor}` - Productos por proveedor
- `GET /api/v1/productos/buscar/{texto}` - Buscar productos por nombre
- `GET /api/v1/productos/vencidos` - Productos vencidos
- `GET /api/v1/productos/proximos-vencer/{dias}` - Productos próximos a vencer

**Proveedores**
- `GET /api/v1/proveedores` - Listar todos los proveedores
- `POST /api/v1/proveedores` - Crear proveedor
- `GET /api/v1/proveedores/{codigoProveedor}` - Obtener proveedor por código
- `PUT /api/v1/proveedores/{codigoProveedor}` - Actualizar proveedor
- `DELETE /api/v1/proveedores/{codigoProveedor}` - Eliminar proveedor
- `POST /api/v1/proveedores/bulk` - Crear múltiples proveedores
- `GET /api/v1/proveedores/buscar/{texto}` - Buscar proveedores por nombre

#### API v2 (REST con HATEOAS)
- `GET /api/v2/productos` - Listado con hipermedia
- `GET /api/v2/productos/{id}` - Producto individual con enlaces
- `GET /api/v2/proveedores` - Proveedores con hipermedia
- `GET /api/v2/proveedores/{codigoProveedor}` - Proveedor individual con enlaces

## 📊 Modelo de Datos

### Entidad Producto
```java
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = true)
    private LocalDate fechaVencimiento;
    
    @Column(nullable = false)
    private String categoria;
    
    @Column(nullable = false)
    private int cantidad;
    
    @ManyToOne
    @JoinColumn(name = "codigo_proveedor")
    private Proveedor proveedor;
}
```

### Entidad Proveedor
```java
@Entity
public class Proveedor {
    @Id
    @Column(columnDefinition = "INT(3)")
    private Integer codigoProveedor;
    
    @Column(nullable = false)
    private String nombreProveedor;
}
```

## 🧪 Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas específicas
mvn test -Dtest=ProductoServiceTest
mvn test -Dtest=ProveedorServiceTest
```

## 📋 Ejemplos de Uso

### Crear un Producto
```bash
curl -X POST http://localhost:8080/api/v1/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Detergente Ecológico",
    "descripcion": "Biodegradable para ropa, 2L",
    "fechaVencimiento": "2026-01-05",
    "categoria": "Limpieza",
    "cantidad": 50,
    "proveedor": {
      "codigoProveedor": 101
    }
  }'
```

### Buscar Productos Próximos a Vencer
```bash
curl -X GET http://localhost:8080/api/v1/productos/proximos-vencer/30
```

### Obtener Productos con HATEOAS
```bash
curl -X GET http://localhost:8080/api/v2/productos \
  -H "Accept: application/hal+json"
```

## 🌐 Acceso Remoto

Para compartir la aplicación en tu red local:

1. **Obtener tu IP** (Windows):
   ```cmd
   ipconfig
   ```

2. **Acceder desde otro dispositivo**:
   ```
   http://TU_IP:8080/api/v1/productos
   ```

## 🔧 Configuración de Perfiles

### Desarrollo (dev)
- Base de datos local MySQL
- Generación automática de datos de prueba
- Logs habilitados

### Testing (test)
- Base de datos separada para pruebas
- Sin datos de prueba automáticos

### Producción (aws)
- Base de datos en AWS RDS
- Configuración optimizada para producción

## 📈 Funcionalidades Avanzadas

### Control de Vencimientos
- 📅 Identificación automática de productos vencidos
- ⏰ Alertas de productos próximos a vencer
- 📊 Filtros por rangos de fechas

### Búsquedas Inteligentes
- 🔍 Búsqueda insensible a mayúsculas/minúsculas
- 📝 Búsqueda parcial por nombre
- 🏪 Filtrado por proveedor

### HATEOAS Implementation
- 🔗 Enlaces automáticos en respuestas JSON
- 📋 Navegación hipermedia
- 🎯 Cumplimiento del nivel 3 de Richardson Maturity Model

## 🚨 Manejo de Errores

La API maneja los siguientes códigos de estado:

| Código | Descripción |
|--------|-------------|
| 200 | Operación exitosa |
| 201 | Recurso creado |
| 204 | Sin contenido |
| 400 | Solicitud incorrecta |
| 404 | Recurso no encontrado |
| 500 | Error interno del servidor |

## 👥 Autor

**Proyecto educativo**
- Curso: DESARROLLO FULL STACK I
- Profesor: Ignacio Cuturrufo
- Estudiante: Carlos Poblete Ponce

## 📄 Licencia

Este proyecto es parte de un trabajo universitario y tiene fines educativos.

---

**EcoMarket SPA** - Comprometidos con un futuro sostenible 🌱
