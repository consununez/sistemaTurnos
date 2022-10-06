package ar.com.sistema.service;

import ar.com.sistema.dto.PrestadorDTO;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.NoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PrestadorServiceTest {

    @Autowired
    IPrestadorService prestadorService;

    private final Random rand = new Random();

    public PrestadorDTO crearNuevoPrestador(){
        PrestadorDTO prestador = new PrestadorDTO();
        prestador.setNombre("Prestador");
        prestador.setApellido("de Prueba");
        prestador.setMatricula(String.valueOf(rand.nextInt(9000)+1000));

        return prestador;
    }

    @Test
    void prestadorGuardarTest() throws NoEncontradoException, ConflictoException {

        PrestadorDTO prestador = crearNuevoPrestador();

        PrestadorDTO prestadorGuardado = prestadorService.guardar(prestador);

        PrestadorDTO prestadorEncontrado = prestadorService.buscar(prestadorGuardado.getId());

        assertEquals("Prestador", prestadorEncontrado.getNombre());
    }

    @Test
    void prestadorBuscarTest() throws NoEncontradoException, ConflictoException {

        PrestadorDTO prestador = crearNuevoPrestador();

        PrestadorDTO prestadorGuardado = prestadorService.guardar(prestador);

        PrestadorDTO prestadorEncontrado = prestadorService.buscar(prestadorGuardado.getId());

        assertEquals("Prestador", prestadorEncontrado.getNombre());
    }

    @Test
    void prestadorListarTodosTest() throws ConflictoException {


        prestadorService.guardar(crearNuevoPrestador());
        prestadorService.guardar(crearNuevoPrestador());
        prestadorService.guardar(crearNuevoPrestador());

        List<PrestadorDTO> prestadors = prestadorService.listarTodos();

        assertTrue(prestadors.size() >= 3);

    }

    @Test
    void prestadorEliminarTest() throws NoEncontradoException, ConflictoException {

        PrestadorDTO prestador = crearNuevoPrestador();

        PrestadorDTO prestadorGuardado = prestadorService.guardar(prestador);

        Long id = prestadorGuardado.getId();

        PrestadorDTO prestadorEncontrado = prestadorService.buscar(id);

        assertEquals("Prestador", prestadorEncontrado.getNombre());

        prestadorService.eliminar(id);

        NoEncontradoException thrown = assertThrows(NoEncontradoException.class, () -> {
            prestadorService.buscar(id);
        });

        assertEquals("Odontólogo con Id " + id + " no encontrado.", thrown.getMessage());



    }

    @Test
    void prestadorActualizarTest() throws NoEncontradoException, ConflictoException {

        PrestadorDTO prestador = crearNuevoPrestador();

        PrestadorDTO prestadorGuardado = prestadorService.guardar(prestador);

        Long id = prestadorGuardado.getId();

        assertEquals("Prestador", prestadorGuardado.getNombre());

        PrestadorDTO prestadorActualizar = prestadorGuardado;
        prestadorActualizar.setNombre("Doc. Emmet");
        prestadorActualizar.setApellido("Brown");
        prestadorActualizar.setMatricula(String.valueOf(rand.nextInt(9000)+1000));

        prestadorService.actualizar(prestadorActualizar);

        PrestadorDTO prestadorEncontrado = prestadorService.buscar(id);

        assertEquals("Doc. Emmet", prestadorEncontrado.getNombre());

    }
}
