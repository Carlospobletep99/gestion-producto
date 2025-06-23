package com.inventario.inventario;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.inventario.inventario.model.Proveedor;
import com.inventario.inventario.repository.ProveedorRepository;
import com.inventario.inventario.service.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProveedorServiceTest {

    // Inyecta el servicio de Proveedor para ser probado.
    @Autowired
    private ProveedorService proveedorService;

    // Crea un mock del repositorio de Proveedor para simular su comportamiento.
    @MockBean
    private ProveedorRepository proveedorRepository;

    @Test
    public void testFindAll() {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Proveedor.
        when(proveedorRepository.findAll()).thenReturn(List.of(new Proveedor(1, "EcoSuppliers S.A.")));

        // Llama al método findAll() del servicio.
        List<Proveedor> proveedores = proveedorService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente un Proveedor.
        assertNotNull(proveedores);
        assertEquals(1, proveedores.size());
        assertEquals("EcoSuppliers S.A.", proveedores.get(0).getNombreProveedor());
    }

    @Test
    public void testFindByCodigoProveedor() {
        Integer codigoProveedor = 1;
        Proveedor proveedor = new Proveedor(codigoProveedor, "GreenWorld Suppliers");

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve un Proveedor opcional.
        when(proveedorRepository.findById(codigoProveedor)).thenReturn(Optional.of(proveedor));

        // Llama al método findByCodigoProveedor() del servicio.
        Proveedor found = proveedorService.findByCodigoProveedor(codigoProveedor);

        // Verifica que el Proveedor devuelto no sea nulo y que su código coincida con el código esperado.
        assertNotNull(found);
        assertEquals(codigoProveedor, found.getCodigoProveedor());
        assertEquals("GreenWorld Suppliers", found.getNombreProveedor());
    }

    @Test
    public void testSave() {
        Proveedor proveedor = new Proveedor(1, "Distribuidora Verde");

        // Define el comportamiento del mock: cuando se llame a save(), devuelve el Proveedor proporcionado.
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        // Llama al método save() del servicio.
        Proveedor saved = proveedorService.save(proveedor);

        // Verifica que el Proveedor guardado no sea nulo y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Distribuidora Verde", saved.getNombreProveedor());
        assertEquals(Integer.valueOf(1), saved.getCodigoProveedor());
    }

    @Test
    public void testDelete() {
        Integer codigoProveedor = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(proveedorRepository).deleteById(codigoProveedor);

        // Llama al método delete() del servicio.
        proveedorService.delete(codigoProveedor);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(proveedorRepository, times(1)).deleteById(codigoProveedor);
    }

    @Test
    public void testGuardarProveedores() {
        List<Proveedor> proveedores = List.of(
            new Proveedor(1, "EcoTech Solutions"),
            new Proveedor(2, "Natural Products Inc"),
            new Proveedor(3, "BioDegradable Supplies")
        );

        // Define el comportamiento del mock: cuando se llame a saveAll(), devuelve la lista proporcionada.
        when(proveedorRepository.saveAll(proveedores)).thenReturn(proveedores);

        // Llama al método guardarProveedores() del servicio.
        List<Proveedor> saved = proveedorService.guardarProveedores(proveedores);

        // Verifica que la lista guardada no sea nula y contenga la cantidad correcta de proveedores.
        assertNotNull(saved);
        assertEquals(3, saved.size());
        assertEquals("EcoTech Solutions", saved.get(0).getNombreProveedor());
        assertEquals("Natural Products Inc", saved.get(1).getNombreProveedor());
        assertEquals("BioDegradable Supplies", saved.get(2).getNombreProveedor());
    }

    @Test
    public void testFindByNombreContaining() {
        String textoBusqueda = "Eco";
        List<Proveedor> proveedoresEncontrados = List.of(
            new Proveedor(1, "EcoSuppliers Ltd"),
            new Proveedor(2, "Eco-Friendly Distributors")
        );

        // Define el comportamiento del mock
        when(proveedorRepository.findByNombreProveedorContainingIgnoreCase(textoBusqueda))
            .thenReturn(proveedoresEncontrados);

        // Llama al método del servicio
        List<Proveedor> result = proveedorService.findByNombreContaining(textoBusqueda);

        // Verifica los resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getNombreProveedor().contains("Eco"));
        assertTrue(result.get(1).getNombreProveedor().contains("Eco"));
    }
}
