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

    //Inyecta el servicio de Proveedor para ser probado.
    @Autowired
    private ProveedorService proveedorService;

    //Crea un mock del repositorio de Proveedor para simular su comportamiento.
    @MockBean
    private ProveedorRepository proveedorRepository;

    // LISTAR TODOS LOS PROVEEDORES:
    @Test
    public void testFindAll() {
        //Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Proveedor.
        when(proveedorRepository.findAll()).thenReturn(List.of(new Proveedor(1, "PROVEEDOR TEST")));

        //Llama al método findAll() del servicio.
        List<Proveedor> proveedores = proveedorService.findAll();

        //Verifica que la lista devuelta no sea nula y contenga exactamente un Proveedor.
        assertNotNull(proveedores);
        assertEquals(1, proveedores.size());
        assertEquals("PROVEEDOR TEST", proveedores.get(0).getNombreProveedor());
    }

    // LISTAR PROVEEDOR POR ID:
    @Test
    public void testFindByCodigoProveedor() {
        Integer codigoProveedor = 1;
        Proveedor proveedor = new Proveedor(codigoProveedor, "PROVEEDOR TEST 2");

        //Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve un Proveedor opcional.
        when(proveedorRepository.findById(codigoProveedor)).thenReturn(Optional.of(proveedor));

        //Llama al método findByCodigoProveedor() del servicio.
        Proveedor found = proveedorService.findByCodigoProveedor(codigoProveedor);

        //Verifica que el Proveedor devuelto no sea nulo y que su código coincida con el código esperado.
        assertNotNull(found);
        assertEquals(codigoProveedor, found.getCodigoProveedor());
        assertEquals("PROVEEDOR TEST 2", found.getNombreProveedor());
    }

    // GUARDAR O ACTUALIZAR PROVEEDOR:
    @Test
    public void testSave() {
        Proveedor proveedor = new Proveedor(1, "PROVEEDOR TEST 3");

        //Define el comportamiento del mock: cuando se llame a save(), devuelve el Proveedor proporcionado.
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);

        //Llama al método save() del servicio.
        Proveedor saved = proveedorService.save(proveedor);

        //Verifica que el Proveedor guardado no sea nulo y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("PROVEEDOR TEST 3", saved.getNombreProveedor());
        assertEquals(Integer.valueOf(1), saved.getCodigoProveedor());
    }

    // ELIMINAR PROVEEDOR POR ID:
    @Test
    public void testDelete() {
        Integer codigoProveedor = 1;

        //Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(proveedorRepository).deleteById(codigoProveedor);

        //Llama al método delete() del servicio.
        proveedorService.delete(codigoProveedor);

        //Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(proveedorRepository, times(1)).deleteById(codigoProveedor);
    }
}
