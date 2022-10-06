package ar.com.sistema.service;

import ar.com.sistema.dto.UsuarioDTO;
import ar.com.sistema.entity.Domicilio;
import ar.com.sistema.exceptions.ConflictoException;
import ar.com.sistema.exceptions.DatosIncorrectosException;
import ar.com.sistema.exceptions.NoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UsuarioServiceTest {


    @Autowired
    IUsuarioService usuarioService;

    public UsuarioDTO crearUsuarioNuevo(){
        Random rand = new Random();

        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Don Bosco");
        domicilio.setNumero("5544");
        domicilio.setLocalidad("CABA");
        domicilio.setProvincia("CABA");

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setNombre("Usuario");
        usuario.setApellido("de Prueba");
        usuario.setDni(String.valueOf(rand.nextInt(31000000)+2000000));
        usuario.setFechaIngreso(LocalDate.of(2002,5,5));
        usuario.setDomicilio(domicilio);

        return usuario;
    }

    @Test
    void usuarioGuardarTest() throws ConflictoException, NoEncontradoException, DatosIncorrectosException {

        UsuarioDTO usuario = crearUsuarioNuevo();

        UsuarioDTO usuarioGuardado = usuarioService.guardar(usuario);

        UsuarioDTO usuarioEncontrado = usuarioService.buscar(usuarioGuardado.getId());
        System.out.println(usuarioEncontrado);
        System.out.println(usuarioEncontrado.getDomicilio().toString());
        assertEquals("Usuario", usuarioEncontrado.getNombre());
    }

    @Test
    void usuarioBuscarTest() throws ConflictoException, NoEncontradoException, DatosIncorrectosException {

        UsuarioDTO usuario = crearUsuarioNuevo();

        UsuarioDTO usuarioGuardado = usuarioService.guardar(usuario);

        UsuarioDTO usuarioEncontrado = usuarioService.buscar(usuarioGuardado.getId());

        assertEquals("Usuario", usuarioEncontrado.getNombre());
    }

    @Test
    void usuarioListarTodosTest() throws ConflictoException, DatosIncorrectosException {


        usuarioService.guardar(crearUsuarioNuevo());
        usuarioService.guardar(crearUsuarioNuevo());
        usuarioService.guardar(crearUsuarioNuevo());

        List<UsuarioDTO> usuarios = usuarioService.listarTodos();

        assertTrue(usuarios.size() >= 3);

    }

    @Test
    void usuarioEliminarTest() throws ConflictoException, NoEncontradoException, DatosIncorrectosException {

        UsuarioDTO usuario = crearUsuarioNuevo();

        UsuarioDTO usuarioGuardado = usuarioService.guardar(usuario);

        Long id = usuarioGuardado.getId();

        UsuarioDTO usuarioEncontrado = usuarioService.buscar(id);

        assertEquals("Usuario", usuarioEncontrado.getNombre());

        usuarioService.eliminar(id);


        NoEncontradoException thrown = assertThrows(NoEncontradoException.class, () -> {
            usuarioService.buscar(id);
        });

        assertEquals("Usuario con Id " + id + " no encontrado.", thrown.getMessage());


    }

    @Test
    void usuarioActualizarTest() throws NoEncontradoException, ConflictoException, DatosIncorrectosException {

        UsuarioDTO usuario = crearUsuarioNuevo();

        UsuarioDTO usuarioGuardado = usuarioService.guardar(usuario);

        Long id = usuarioGuardado.getId();

        assertEquals("Usuario", usuarioGuardado.getNombre());

        UsuarioDTO usuarioActualizar = usuarioGuardado;
        usuarioActualizar.setNombre("Marty");
        usuarioActualizar.setApellido("McFly");
        usuarioActualizar.getDomicilio().setCalle("Calle falsa");

        usuarioService.actualizar(usuarioActualizar);

        UsuarioDTO usuarioEncontrado = usuarioService.buscar(id);

        assertEquals("Marty", usuarioEncontrado.getNombre());
        assertEquals("Calle falsa", usuarioEncontrado.getDomicilio().getCalle());


    }

}
