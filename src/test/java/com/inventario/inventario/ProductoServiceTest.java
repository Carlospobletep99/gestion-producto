package com.inventario.inventario;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.inventario.inventario.model.Producto;
import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.repository.ProductoRepository;
import com.inventario.inventario.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductoServiceTest {

    // Inyecta el servicio de Producto para ser probado.
    @Autowired
    private ProductoService productoService;

    // Crea un mock del repositorio de Producto para simular su comportamiento.
    @MockBean
    private ProductoRepository productoRepository;

    @Test
    public void testFindAll() {
        // Crea un proveedor de prueba
        Proveedor proveedor = new Proveedor(1, "EcoSupplier");
        
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Producto.
        when(productoRepository.findAll()).thenReturn(List.of(
            new Producto(1, "Jabón Ecológico", "Jabón biodegradable", 
                        LocalDate.now().plusDays(365), "Higiene", 50, proveedor)
        ));

        // Llama al método findAll() del servicio.
        List<Producto> productos = productoService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente un Producto.
        assertNotNull(productos);
        assertEquals(1, productos.size());
        assertEquals("Jabón Ecológico", productos.get(0).getNombre());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Proveedor proveedor = new Proveedor(1, "EcoSupplier");
        Producto producto = new Producto(id, "Detergente Ecológico", "Detergente biodegradable", 
                                       LocalDate.now().plusDays(180), "Limpieza", 30, proveedor);

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve un Producto opcional.
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));

        // Llama al método findById() del servicio.
        Producto found = productoService.findById(id);

        // Verifica que el Producto devuelto no sea nulo y que su id coincida con el id esperado.
        assertNotNull(found);
        assertEquals(id, found.getId());
        assertEquals("Detergente Ecológico", found.getNombre());
    }

    @Test
    public void testSave() {
        Proveedor proveedor = new Proveedor(1, "EcoSupplier");
        Producto producto = new Producto(1, "Champú Natural", "Champú sin químicos", 
                                       LocalDate.now().plusDays(300), "Cuidado Personal", 25, proveedor);

        // Define el comportamiento del mock: cuando se llame a save(), devuelve el Producto proporcionado.
        when(productoRepository.save(producto)).thenReturn(producto);

        // Llama al método save() del servicio.
        Producto saved = productoService.save(producto);

        // Verifica que el Producto guardado no sea nulo y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Champú Natural", saved.getNombre());
        assertEquals("Cuidado Personal", saved.getCategoria());
    }

    @Test
    public void testDelete() {
        Integer id = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(productoRepository).deleteById(id);

        // Llama al método delete() del servicio.
        productoService.delete(id);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el id proporcionado.
        verify(productoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFindByProveedor() {
        Proveedor proveedor = new Proveedor(1, "EcoSupplier");
        List<Producto> productosProveedor = List.of(
            new Producto(1, "Producto Eco 1", "Descripción 1", 
                        LocalDate.now().plusDays(100), "Categoria 1", 20, proveedor),
            new Producto(2, "Producto Eco 2", "Descripción 2", 
                        LocalDate.now().plusDays(200), "Categoria 2", 15, proveedor)
        );

        // Define el comportamiento del mock
        when(productoRepository.findByProveedor(proveedor)).thenReturn(productosProveedor);

        // Llama al método del servicio
        List<Producto> result = productoService.findByProveedor(proveedor);

        // Verifica los resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(proveedor.getCodigoProveedor(), result.get(0).getProveedor().getCodigoProveedor());
    }

    @Test
    public void testFindByNombreContaining() {
        String textoBusqueda = "Eco";
        Proveedor proveedor = new Proveedor(1, "EcoSupplier");
        List<Producto> productosEncontrados = List.of(
            new Producto(1, "EcoJabón", "Jabón ecológico", 
                        LocalDate.now().plusDays(365), "Higiene", 40, proveedor)
        );

        // Define el comportamiento del mock
        when(productoRepository.findByNombreContainingIgnoreCase(textoBusqueda)).thenReturn(productosEncontrados);

        // Llama al método del servicio
        List<Producto> result = productoService.findByNombreContaining(textoBusqueda);

        // Verifica los resultados
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getNombre().contains("Eco"));
    }
}